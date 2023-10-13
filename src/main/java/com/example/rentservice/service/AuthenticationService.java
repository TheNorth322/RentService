package com.example.rentservice.service;

import com.example.rentservice.dto.auth.AuthenticationRequest;
import com.example.rentservice.dto.auth.AuthenticationResponse;
import com.example.rentservice.dto.auth.RegisterRequest;
import com.example.rentservice.dto.email.EmailDetails;
import com.example.rentservice.entity.auth.EmailVerificationTokenEntity;
import com.example.rentservice.entity.auth.PasswordResetTokenEntity;
import com.example.rentservice.entity.auth.RefreshToken;
import com.example.rentservice.entity.user.UserEntity;
import com.example.rentservice.exception.auth.*;
import com.example.rentservice.repository.EmailVerificationTokenRepository;
import com.example.rentservice.repository.PasswordResetTokenRepository;
import com.example.rentservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final String apiUrl = "http://localhost::8080";

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private RefreshTokenService refreshTokenService;

    @Autowired
    private PasswordResetTokenRepository passwordResetTokenRepository;

    @Autowired
    private EmailVerificationTokenRepository emailVerificationTokenRepository;

    @Autowired
    private EmailService emailService;

    public AuthenticationResponse register(RegisterRequest request) throws UserNotFoundException, EmailIsOccupiedException, UsernameIsOccupiedException {
        validateReqisterRequest(request);

        UserEntity user = UserEntity
                .builder()
                .username(request.getUsername())
                .emailVerified(false)
                .role(request.getRole())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .build();

        userRepository.save(user);

        String jwtToken = jwtService.generateToken(user);
        RefreshToken refreshToken = refreshTokenService.createRefreshToken(user.getUsername());

        return AuthenticationResponse
                .builder()
                .accessToken(jwtToken)
                .refreshToken(refreshToken.getToken())
                .build();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) throws UserNotFoundException {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );

        UserEntity user = userRepository.findByUsername(request.getUsername()).orElseThrow(() -> new UserNotFoundException("User not found"));
        String jwtToken = jwtService.generateToken(user);
        RefreshToken refreshToken = refreshTokenService.createRefreshToken(user.getUsername());

        return AuthenticationResponse
                .builder()
                .accessToken(jwtToken)
                .refreshToken(refreshToken.getToken())
                .build();
    }

    public String forgotPassword(String email) throws UserNotFoundException {
        UserEntity userEntity = userRepository.findByEmail(email).orElseThrow(() -> new UserNotFoundException("User not found"));

        PasswordResetTokenEntity token = generatePasswordResetToken(userEntity);
        passwordResetTokenRepository.save(token);
        String url = apiUrl + "/auth/reset_password?token=" + token.getToken();
        EmailDetails emailDetails = getEmailDetails(url, email, "Click the link to reset password: ", "Password reset");

        return emailService.sendMail(emailDetails);
    }

    public String validatePasswordResetToken(String token) throws PasswordResetTokenNotFoundException, PasswordResetTokenIsExpiredException {
        PasswordResetTokenEntity tokenEntity = passwordResetTokenRepository.findByToken(token).orElseThrow(() -> new PasswordResetTokenNotFoundException("Password reset token not found"));;

        ValidatePasswordResetToken(tokenEntity);

        return "Token validated successfully";
    }

    public String resetPassword(String password, String token) throws PasswordResetTokenNotFoundException {
        PasswordResetTokenEntity tokenEntity = passwordResetTokenRepository.findByToken(token).orElseThrow(() -> new PasswordResetTokenNotFoundException("Password reset token not found"));
        UserEntity userEntity = tokenEntity.getUser();

        userEntity.setPassword(passwordEncoder.encode(password));
        userEntity.setPasswordResetToken(null);
        userRepository.save(userEntity);

        passwordResetTokenRepository.delete(tokenEntity);

        return "Password reset succeeded";
    }

    public String sendVerificationEmail(String email) throws UserNotFoundException {
        UserEntity userEntity = userRepository.findByEmail(email).orElseThrow(() -> new UserNotFoundException("User not found"));

        EmailVerificationTokenEntity token = generateEmailVerificationToken(userEntity);
        emailVerificationTokenRepository.save(token);
        String url = apiUrl + "/auth/verify_email?token=" + token.getToken();
        EmailDetails emailDetails = getEmailDetails(url, email, "Click the link to verify email: ", "Email verification");

        return emailService.sendMail(emailDetails);
    }

    public String validateEmailVerificationToken(String token) throws EmailVerificationTokenNotFoundException, EmailVerificationTokenIsExpiredException {
        EmailVerificationTokenEntity tokenEntity = emailVerificationTokenRepository.findByToken(token)
                .orElseThrow(() -> new EmailVerificationTokenNotFoundException("Email verification token not found"));

        ValidateEmailVerificationToken(tokenEntity);

        UserEntity userEntity = tokenEntity.getUser();

        userEntity.setEmailVerified(true);
        userEntity.setEmailVerificationToken(null);
        userRepository.save(userEntity);
        tokenEntity.setUser(null);

        emailVerificationTokenRepository.delete(tokenEntity);

        return "Token validated successfully";
    }
    private EmailVerificationTokenEntity generateEmailVerificationToken(UserEntity user) {
        return EmailVerificationTokenEntity
                .builder()
                .token(UUID.randomUUID().toString())
                .user(user)
                .expiryDate(new Date(System.currentTimeMillis() + 1000 * 60 * 5))
                .build();
    }

    private void ValidateEmailVerificationToken(EmailVerificationTokenEntity token) throws EmailVerificationTokenIsExpiredException {
        if (token.isExpired()) {
            emailVerificationTokenRepository.delete(token);
            throw new EmailVerificationTokenIsExpiredException("Password reset token is expired!");
        }
    }

    private void ValidatePasswordResetToken(PasswordResetTokenEntity token) throws PasswordResetTokenIsExpiredException {
        if (token.isExpired()) {
            passwordResetTokenRepository.delete(token);
            throw new PasswordResetTokenIsExpiredException("Password reset token is expired!");
        }
    }
    private EmailDetails getEmailDetails(String url, String email, String msgBody, String subject) {
        return EmailDetails
                .builder()
                .recipient(email)
                .msgBody(msgBody + url)
                .subject(subject)
                .build();
    }
    private PasswordResetTokenEntity generatePasswordResetToken(UserEntity user) {
        return PasswordResetTokenEntity
                .builder()
                .token(UUID.randomUUID().toString())
                .user(user)
                .expiryDate(new Date(System.currentTimeMillis() + 1000 * 60 * 5))
                .build();
    }

    private void validateReqisterRequest(RegisterRequest request) throws EmailIsOccupiedException, UsernameIsOccupiedException {
        if (userRepository.findByEmail(request.getEmail()).isPresent())
            throw new EmailIsOccupiedException("Account with this email is already registered");
        if (userRepository.findByUsername(request.getUsername()).isPresent())
            throw new UsernameIsOccupiedException("Account with this username is already registered");
    }
}
