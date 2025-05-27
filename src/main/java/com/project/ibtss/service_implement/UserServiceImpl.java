package com.project.ibtss.service_implement;

import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jwt.JWTClaimsSet;
import com.project.ibtss.dto.request.LoginRequest;
import com.project.ibtss.dto.request.RegisterRequest;
import com.project.ibtss.dto.response.UserResponse;
import com.project.ibtss.enums.ErrorCode;
import com.project.ibtss.enums.Role;
import com.project.ibtss.enums.TokenStatus;
import com.project.ibtss.enums.TokenType;
import com.project.ibtss.exception.AppException;
import com.project.ibtss.mapper.UserMapper;
import com.project.ibtss.model.Token;
import com.project.ibtss.model.User;
import com.project.ibtss.repository.TokenRepository;
import com.project.ibtss.repository.UserRepository;
import com.project.ibtss.service.JWTService;
import com.project.ibtss.service.UserService;
import lombok.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private static final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;
    private final TokenRepository tokenRepository;
    private final JWTService jwtService;

    @Value("${jwt.signerKey}")
    private String SIGNER_KEY;

    @Override
    public UserResponse userDetail() {
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByUsername(userName).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));

        return userMapper.toUserResponse(user);
    }

    //for test api
    @Override
    public UserResponse login(LoginRequest loginRequest) {
        User user = userRepository.findByUsername(loginRequest.getUsername()).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));

        if(!passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())){
            throw new AppException(ErrorCode.INCORRECT_PASSWORD);
        }

//        if(!loginRequest.getToken().isEmpty()){
//            Token refreshToken = tokenRepository.findByUser_IdAndStatusAndType(user.getId(), TokenStatus.ACTIVE.getValue(), TokenType.REFRESH.getValue()).orElse(null);
//            if(refreshToken == null){
//                refreshToken = jwtService.generateRefreshToken(user);
//            }
//        }

        UserResponse userResponse = userMapper.toUserResponse(user);
        userResponse.setToken(generateToken(user));

        return userResponse;
    }

    private String generateToken(User user) {
        JWSHeader header = new JWSHeader(JWSAlgorithm.HS512);

        JWTClaimsSet jwtClaimsSet = new JWTClaimsSet.Builder()
                .subject(user.getUsername())
                .issuer("aia.com")
                .issueTime(new Date())
                .expirationTime(new Date(
                        Instant.now().plus(1, ChronoUnit.HOURS).toEpochMilli()
                ))
                .claim("scope", buildScope(user))
                .build();

        Payload payload = new Payload(jwtClaimsSet.toJSONObject());

        JWSObject jwsObject = new JWSObject(header, payload);

        try {
            jwsObject.sign(new MACSigner(SIGNER_KEY.getBytes()));
            return jwsObject.serialize();
        } catch (JOSEException e) {
            log.error("Can't create jwt token!");
            throw new RuntimeException(e);
        }
    }

    private String buildScope(User user) {
        Role role = user.getRole();
        return (role != null) ? role.getRoleName() : " ";
    }

    //for test api
    public String register(RegisterRequest registerRequest){
        if(!registerRequest.getPassword().equals(registerRequest.getConfirmPassword())){
            log.error("Password and Confirm Password do not match");
            throw new AppException(ErrorCode.PASSWORD_NOT_MATCH);
        }

        if(userRepository.findByUsername(registerRequest.getUsername()).isPresent()){
            log.error("Username already exists");
            throw new AppException(ErrorCode.USERNAME_EXISTED);
        }

        User user = User.builder()
                .username(registerRequest.getUsername())
                .password(passwordEncoder.encode(registerRequest.getPassword()))
                .build();

        userRepository.save(user);
        return "Success";
    }
}
