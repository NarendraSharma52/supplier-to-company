package com.supplify.supplier_to_company.security;
import com.supplify.supplier_to_company.security.JwtUtil;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
public class JwtFilter extends OncePerRequestFilter {

    @Autowired
    JwtUtil jwtUtil;

    // URLs to skip
    private static final List<String> EXCLUDED_URLS = List.of(
            "/s2c/api/v1/auth/login",
            "/s2c/api/v1/supplier/start-registration"
    );

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        String path = request.getServletPath();
        return EXCLUDED_URLS.stream().anyMatch(path::startsWith); // skip these URLs
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        String jwtToken = request.getHeader("Authorization");

        // If no token → just move ahead
        if (jwtToken == null || jwtToken.isBlank()) {
            filterChain.doFilter(request, response);
            return;
        }

        jwtToken = jwtToken.trim();

        // Token validation
        if (!jwtUtil.isTokenValid(jwtToken)) {
            filterChain.doFilter(request, response);
            return;
        }

        // Extract claims
        Claims claims = jwtUtil.decryptToken(jwtToken);

        UsernamePasswordAuthenticationToken auth =
                new UsernamePasswordAuthenticationToken(claims, null, new ArrayList<>());

        SecurityContextHolder.getContext().setAuthentication(auth);

        filterChain.doFilter(request, response);
    }
}
