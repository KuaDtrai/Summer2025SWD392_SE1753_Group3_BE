package com.project.ibtss.service_implement;

import com.project.ibtss.enums.Role;
import com.project.ibtss.enums.TokenStatus;
import com.project.ibtss.model.Account;
import com.project.ibtss.model.Token;
//import com.project.ibtss.model.User;
import com.project.ibtss.repository.AccountRepository;
import com.project.ibtss.repository.TokenRepository;
import com.project.ibtss.service.JWTService;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.StringJoiner;
import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class JWTServiceImpl implements JWTService {

    @Value("${jwt.signerKey}")
    private String secretKey;

    @Value("604800000")
    private Long accessTokenExpiration;

    @Value("31536000000")
    private Long refreshTokenExpiration;

    @Value("900000")
    private Long resetTokenExpiration;

//    private final TokenRepo tokenRepo;
    private final AccountRepository accountRepository;

//    @Override
//    public Role extractRole(String token) {
//        String email = extractEmail(token);
//        if (email == null || email.isEmpty()) {
//            throw new IllegalArgumentException("Email is missing in JWT token");
//        }
//        Optional<Account> account = accountRepo.findByEmail(email);
//        if (account.isEmpty()) {
//            throw new IllegalArgumentException("Account not found for email: "+ email);
//        }
//        return account.get().getRole();
//    }

    @Override
    public String extractEmail(String token) {
        return getClaim(token, Claims::getSubject);
    }

    @Override
    public String extractPhone(String token) {
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


    private String generateToken(Map<String, Object> claims, Account account, Long expiredTime) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(account.getPhone())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis(   ) + expiredTime))
                .signWith(getSigningKey())
                .compact();
    }
    @Override
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(getSigningKey()) // Chuyển key thành byte[]
                    .build()
                    .parseClaimsJws(token); // Nếu token hợp lệ sẽ không có lỗi
            return true;
        } catch (ExpiredJwtException e) {
            System.out.println("Token hết hạn!");
        } catch (UnsupportedJwtException e) {
            System.out.println("Token không được hỗ trợ!");
        } catch (MalformedJwtException e) {
            System.out.println("Token không đúng định dạng!");
        } catch (SignatureException e) {
            System.out.println("Chữ ký không hợp lệ!");
        } catch (IllegalArgumentException e) {
            System.out.println("Token rỗng hoặc null!");
        }
        return false;
    }


    @Override
    public String generateAccessToken(Account account) {
        return generateToken(new HashMap<>(), account, accessTokenExpiration);
    }

    @Override
    public String generateRefreshToken(Account userDetails) {
        return generateToken(new HashMap<>(), userDetails, refreshTokenExpiration);
    }

    @Override
    public String generateResetToken(Account userDetails) {
        return generateToken(new HashMap<>(), userDetails, resetTokenExpiration);
    }

    @Override
    public Token checkTokenIsValid(String tokenType, Account account) {

//        Token token = tokenRepo.findByAccount_IdAndStatusAndType(account.getId(), TokenStatus.ACTIVE.getValue(), tokenType).orElse(null);
//
//        if(token != null) {
//            if(getClaim(token.getValue(), Claims::getExpiration).before(new Date())) {
//                token.setStatus(TokenStatus.EXPIRED.getValue());
//                tokenRepo.save(token);
//                return null;
//            }
//        }
//
//        return token;
        return null;
    }

}
