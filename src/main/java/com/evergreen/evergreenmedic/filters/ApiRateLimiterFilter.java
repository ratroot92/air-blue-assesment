package com.evergreen.evergreenmedic.filters;

import com.evergreen.evergreenmedic.repositories.RouteRateLimiterRepository;
import com.evergreen.evergreenmedic.repositories.UserRepository;
import com.evergreen.evergreenmedic.services.RateLimiterService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@AllArgsConstructor
@Slf4j
public class ApiRateLimiterFilter extends OncePerRequestFilter implements Ordered {
    private final RouteRateLimiterRepository routeRateLimiterRepository;
    private final RateLimiterService rateLimiterService;
    private final UserRepository userRepository;
//    private List<String> excludedUris = new ArrayList<String>(Arrays.asList("/api/v1/auth/login", "/api/v1/auth/register"));


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
//        if (!"/api/v1/auth/login".equals(request.getRequestURI()) && !"/api/v1/auth/register".equals(request.getRequestURI())) {
//            String requestUri = request.getRequestURI().toString().substring(1, request.getRequestURI().toString().length());
//            CustomHttpMethodEnum requestMethod = CustomHttpMethodEnum.valueOf(request.getMethod());
//            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//            UserEntity userEntity = userRepository.findByEmail(authentication.getPrincipal().toString());
//
//            if (userEntity != null && authentication.isAuthenticated()) {
//                Optional<RouteRateLimiterEntity> routeRateLimiterEntity = routeRateLimiterRepository.findByRouteUrlAndRouteMethodAndUserRole(requestUri, requestMethod, userEntity.getRole());
//                if (routeRateLimiterEntity.isPresent()) {
//                    Bucket clientBucket = rateLimiterService.resolveBucket(userEntity.getEmail());
//                    if (clientBucket == null) {
//                        throw new RuntimeException("Bucket could not be resolved. Please subscribe bucket.");
//                    }
//                    boolean triedConsuming = clientBucket.tryConsume(1);
//                    if (!triedConsuming) {
//                        throw new RuntimeException("Bucket consumed");
//
//                    }
//                    log.info("Bucket consumed: {}", triedConsuming);
//                } else {
//                    log.info("Ratelimiter not found for route {}", requestUri);
//                }
//            } else {
//                throw new RuntimeException("Invalid user");
//
//            }
//
//
//        }

        filterChain.doFilter(request, response);
    }

    @Override
    public int getOrder() {
        return -9;
    }
}
