package com.pranav.security;

import com.pranav.service.UserDetailServiceImpl;
import com.pranav.utils.JWTUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
@Component
public class JwtAuthFilter extends OncePerRequestFilter {
    @Autowired
    private JWTUtil jwtUtil;

    @Autowired
    private UserDetailServiceImpl userDetailService;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        final String authHeader = request.getHeader("Authorization");
        String username = null;
        String token = null;

        if(authHeader != null && authHeader.startsWith("Bearer ")){
            token = authHeader.substring(7);

            try {
                username = jwtUtil.extractUsername(token);
            }catch (Exception e){
                System.out.print("Error extracting username from JWT Token");
            }
        }else{
            System.out.println("JWT Token doesn't begin with bearer string.");
        }

        if(username != null && SecurityContextHolder
                .getContext()
                .getAuthentication() == null){
            var userDetails = userDetailService.loadUserByUsername(username);
            if(jwtUtil.validateToken(token, userDetails.getUsername())){
                var authenticationToken = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());
                authenticationToken.
                        setDetails(new WebAuthenticationDetailsSource().
                        buildDetails(request));
                SecurityContextHolder.
                        getContext()
                        .setAuthentication(authenticationToken);
            }
        }
        filterChain.doFilter(request,response);
    }
}
