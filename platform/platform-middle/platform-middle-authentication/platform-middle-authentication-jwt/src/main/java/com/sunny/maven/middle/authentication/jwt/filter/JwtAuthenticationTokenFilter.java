package com.sunny.maven.middle.authentication.jwt.filter;

import com.sunny.maven.middle.authentication.jwt.utils.JwtTokenUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author SUNNY
 * @description: 检测JWT
 * @create: 2023-04-02 12:01
 */
@Component
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {
    private UserDetailsService userDetailsService;

    private JwtTokenUtils jwtTokenUtils;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String authHeader = request.getHeader(jwtTokenUtils.getHeader());
        if (authHeader != null && StringUtils.isNotEmpty(authHeader)) {
            String username = jwtTokenUtils.getUsernameFromToken(authHeader);
            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);
                if (jwtTokenUtils.validateToken(authHeader, userDetails)) {
                    UsernamePasswordAuthenticationToken authentication =
                            new UsernamePasswordAuthenticationToken(userDetails, null,
                                    userDetails.getAuthorities());
                    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            }
        }
        filterChain.doFilter(request, response);
    }
    @Autowired
    public JwtAuthenticationTokenFilter(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
        this.jwtTokenUtils = JwtTokenUtils.getInstance();
    }
}
