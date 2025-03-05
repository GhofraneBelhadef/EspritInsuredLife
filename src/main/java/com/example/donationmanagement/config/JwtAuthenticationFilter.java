package com.example.donationmanagement.config;

import com.example.donationmanagement.services.UserManagement.JwtService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;
import java.util.Collections;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import jakarta.servlet.http.HttpServletRequest;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;

    public JwtAuthenticationFilter(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {
        // Récupère l'URL complète de la requête
        String requestURL = request.getRequestURL().toString();
        // Récupère le header d'autorisation
        String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

        // Si l'URL commence par "/swagger-ui" ou "/v3/api-docs", on autorise l'accès sans token
        if (requestURL.startsWith("/swagger-ui") || requestURL.startsWith("/v3/api-docs")) {
            chain.doFilter(request, response);
            return;
        }

        // Si le header d'autorisation est absent ou mal formé, on continue sans authentification
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            chain.doFilter(request, response);
            return;
        }

        // Extraction du token à partir du header
        String token = authHeader.substring(7);
        // Récupère le nom d'utilisateur du token
        String username = jwtService.extractUsername(token);

        // Si le token est valide et l'utilisateur n'est pas encore authentifié
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            // Crée un utilisateur avec des autorités vides (tu peux ajouter des rôles plus tard si nécessaire)
            UserDetails userDetails = new User(username, "", Collections.emptyList());

            // Vérifie si le token est valide
            if (jwtService.isTokenValid(token, username)) {
                // Crée un token d'authentification
                UsernamePasswordAuthenticationToken authToken =
                        new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

                // Associe l'authentification à la requête
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                // Place l'authentification dans le contexte de sécurité
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }

        chain.doFilter(request, response);
    }
}
