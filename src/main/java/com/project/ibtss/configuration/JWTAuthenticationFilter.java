package com.project.ibtss.configuration;

import com.project.ibtss.enums.TokenStatus;
import com.project.ibtss.model.Account;
import com.project.ibtss.repository.TokenRepository;
import com.project.ibtss.service.JWTService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.core.userdetails.UserDetails;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.jwt.JwtException;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
@Component
@RequiredArgsConstructor
public class JWTAuthenticationFilter extends OncePerRequestFilter {

//    private final UserDetailsService userDetailsService;

    private final JWTService jwtService;

    private final TokenRepository tokenRepository;

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {

        final String authHeader = request.getHeader("Authorization");

        if (authHeader == null || !authHeader.startsWith("Bearer ") || authHeader.length() < 8) {
            filterChain.doFilter(request, response);
            return;
        }

        String jwt = authHeader.substring(7);
        String email;
        try {
            email = jwtService.extractEmail(jwt);
        } catch (JwtException e) {
            log.warn("Invalid JWT: {}", e.getMessage());
            filterChain.doFilter(request, response);
            return;
        }

        if (email!= null && SecurityContextHolder.getContext().getAuthentication() == null) {

//            UserDetails userDetails = userDetailsService.loadUserByUsername(email);
//            if(userDetails == null){
//                throw new BadCredentialsException("Invalid username");
//            }

//            Account account = (Account) userDetails;

            //add later when user have status
//            if (UserStatus.INACTIVE.getValue().equalsIgnoreCase(acc.getStatusAccount())) {
//                filterChain.doFilter(request, response);
//                return;
//            }

            boolean isTokenValid = jwtService.validateToken(jwt);
            boolean isStoredTokenValid = tokenRepository
                    .findByValue(jwt)
                    .filter(t -> t.getStatus().equals(TokenStatus.ACTIVE.getValue()))
                    .isPresent();

            if (isTokenValid && isStoredTokenValid) {
//                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
//                        userDetails,
//                        null,
//                        userDetails.getAuthorities()
//                );
//                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
//                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }

        filterChain.doFilter(request, response);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        String path = request.getServletPath();
        return path.equals("/login") || path.equals("/register");
    }

}
