package com.evergreen.evergreenmedic.filters;


import com.evergreen.evergreenmedic.entities.UserEntity;
import com.evergreen.evergreenmedic.implementations.CustomUserDetailsServiceImpl;
import com.evergreen.evergreenmedic.utils.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

@AllArgsConstructor
@Component
@Slf4j
public class CookieFilter extends OncePerRequestFilter implements Ordered {
    private final CustomUserDetailsServiceImpl customUserDetailsService;
    private final JwtUtil jwtUtil;
    private static final List<String> EXCLUDED_PATHS = List.of("/api/v1/auth/login", "/api/v1/auth/register", "/uploads/");

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String path = request.getRequestURI();
        if (Objects.equals(path, "/api/v1/auth/login") || Objects.equals(path, "/api/v1/auth/register") || Objects.equals(path, "/uploads/")) {
            filterChain.doFilter(request, response);
            log.info("--- CookieFilter Bypassed  {} ---", path);

        } else {
            log.info("--- CookieFilter Start ---");

            String email = null;
            String jwtToken = null;
            log.info("Cookie Filter  getRequestURI {}", path);
            log.info("Cookie Filter  getCookies {}", request.getCookies());
            if (request.getCookies() != null) {
                List<Cookie> cookies = List.of(request.getCookies());
                if (cookies.toArray().length > 0) {
                    for (Cookie cookie : cookies) {
                        String cookieName = cookie.getName();
                        String cookieValue = cookie.getValue();
                        log.info("Cookie Name {}", cookieName);
                        log.info("Cookie Value {}", cookieValue);
                    }
                    jwtToken = cookies.stream().findFirst().get().getValue();
                    try {
                        email = jwtUtil.extractUsername(jwtToken);
                        if (email != null && jwtToken != null) {
                            try {
                                UserEntity userEntity = customUserDetailsService.loadUserByUsername(email);
                                if (userEntity != null) {
                                    log.info("CookieFilter : jwtUtil.validateToken(jwtToken) : {}", jwtUtil.validateToken(jwtToken));
                                    if (jwtUtil.validateToken(jwtToken)) {
                                        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userEntity, null, userEntity.getAuthorities());
//                     using the above line we can get user in controller method
//                    @AuthenticationPrincipal AuthenticatedUserDetail authUser
                                        authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                                        SecurityContextHolder.getContext().setAuthentication(authToken);
                                        request.setAttribute("user", userEntity);
//                  can be utilized as in controller method
//                  @RequestAttribute("user") AuthenticatedUserDetail userEntity
                                    }
                                }
                            } catch (UsernameNotFoundException e) {
                                throw new UsernameNotFoundException(e.getMessage());
                            }
                            log.info("CookieFilter : Request {}:{}", request.getMethod(), request.getRequestURI());
//                        log.info("CookieFilter : userEntity : {}", userEntity);

                        }
                    } catch (Exception e) {
                        System.out.println("CookieFilter : Error in Cookie Filter" + e.getMessage());
                        Cookie cookie = new Cookie("accessToken", null);
                        cookie.setMaxAge(0);
                        cookie.setSecure(true);
                        cookie.setHttpOnly(true);
                        cookie.setPath("/");
                        response.addCookie(cookie);

                    }

                } else {
                    filterChain.doFilter(request, response);
                }
            }
            filterChain.doFilter(request, response);
            log.info("--- CookieFilter End ---");
        }

    }

    @Override
    public int getOrder() {
        return -10;
    }
}

