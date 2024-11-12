package com.evergreen.evergreenmedic.filters;


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
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@AllArgsConstructor
@Component
@Slf4j
public class CookieFilter extends OncePerRequestFilter implements Ordered {
    private final CustomUserDetailsServiceImpl customUserDetailsService;
    private final JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String email = null;
        String jwtToken = null;
        log.info("Cookie Filter {}", request.getRequestURI());
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
                email = jwtUtil.extractUsername(jwtToken);
                if (email != null && jwtToken != null) {
                    UserDetails userDetails = customUserDetailsService.loadUserByUsername(email);

                    log.info("Cookie Filter : Request {}:{}", request.getMethod(), request.getRequestURI());

                    log.info("Cookie Filter : userDetails : {}", userDetails);

                    if (userDetails != null) {

                        log.info("Cookie Filter : jwtUtil.validateToken(jwtToken) : {}", jwtUtil.validateToken(jwtToken));

                        if (jwtUtil.validateToken(jwtToken)) {

                            UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(email, null, userDetails.getAuthorities());

                            authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                            log.info(authToken.toString());

                            SecurityContextHolder.getContext().setAuthentication(authToken);
                        }
                    }
                }
            } else {
                filterChain.doFilter(request, response);

            }
        }


        filterChain.doFilter(request, response);
    }

    @Override
    public int getOrder() {
        return -10;
    }
}

