package com.project.ibtss.service_implement;

import com.project.ibtss.enums.TokenStatus;
import com.project.ibtss.model.Token;
import com.project.ibtss.model.User;
import com.project.ibtss.repository.TokenRepository;
import com.project.ibtss.repository.UserRepository;
import com.project.ibtss.service.JWTService;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class JWTServiceImpl implements JWTService {

    @Value("${jwt.signerkey}")
    private String secretKey;

    @Value("604800000")
    private Long accessTokenExpiration;

    @Value("31536000000")
    private Long refreshTokenExpiration;

    @Value("900000")
    private Long resetTokenExpiration;

    private final TokenRepository tokenRepository;
    private final UserRepository userRepository;

    @Override
    public String extractEmail(String token) {
        return getClaim(token, Claims::getSubject);
    }


    private <T> T getClaim(String token, Function<Claims, T> claimsResolver) {
        return claimsResolver.apply(getAllClaimsFromToken(token));
    }


    private Claims getAllClaimsFromToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private Key getSigningKey() {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(secretKey));
    }


    private String generateToken(Map<String, Object> claims, UserDetails userDetails, Long expiredTime) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis(   ) + expiredTime))
                .signWith(getSigningKey())
                .compact();
    }
    @Override
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(getSigningKey()) // key to byte[]
                    .build()
                    .parseClaimsJws(token); // token valid -> no error
            return true;
        } catch (ExpiredJwtException e) {
            System.out.println("Expired JWT token!");
        } catch (UnsupportedJwtException e) {
            System.out.println("Unsupported JWT token!");
        } catch (MalformedJwtException e) {
            System.out.println("Format of JWT token was invalid!");
        } catch (SignatureException e) {
            System.out.println("Invalid JWT signature!");
        } catch (IllegalArgumentException e) {
            System.out.println("Token is empty or null!");
        }
        return false;
    }


    @Override
    public String generateAccessToken(UserDetails userDetails) {
        return generateToken(new HashMap<>(), userDetails, accessTokenExpiration);
    }

    @Override
    public String generateRefreshToken(UserDetails userDetails) {
        return generateToken(new HashMap<>(), userDetails, refreshTokenExpiration);
    }

    @Override
    public String generateResetToken(UserDetails userDetails) {
        return generateToken(new HashMap<>(), userDetails, resetTokenExpiration);
    }

    @Override
    public Token checkTokenIsValid(String tokenType, User user) {

        Token token = tokenRepository.findByUser_IdAndStatusAndType(user.getId(), TokenStatus.ACTIVE.getValue(), tokenType).orElse(null);

        if(token != null) {
            if(getClaim(token.getValue(), Claims::getExpiration).before(new Date())) {
                token.setStatus(TokenStatus.EXPIRED.getValue());
                tokenRepository.save(token);
                return null;
            }
        }
        return token;
    }
}
