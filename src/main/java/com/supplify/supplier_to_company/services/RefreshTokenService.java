package com.supplify.supplier_to_company.services;

import com.supplify.supplier_to_company.models.RefreshToken;
import com.supplify.supplier_to_company.models.User;
import com.supplify.supplier_to_company.repositories.RefreshTokenRepository;
import com.supplify.supplier_to_company.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class RefreshTokenService {

    @Autowired
    private RefreshTokenRepository refreshTokenRepository;

    @Autowired
    private UserRepository userRepository;

    // Refresh token is valid for 7 days.
    private static final int REFRESH_TOKEN_VALIDITY_DAYS = 7;

    /**
     * Creates a refresh token for the user identified by email, or refreshes
     * the expiry of their existing one (one refresh token per user).
     */
    public RefreshToken create(String email) {
        User user = userRepository.findByEmail(email);

        RefreshToken existing = refreshTokenRepository.findByUser(user).orElse(null);

        if (existing == null) {
            RefreshToken refreshToken = RefreshToken.builder()
                    .token(UUID.randomUUID().toString())
                    .expiryDate(LocalDateTime.now().plusDays(REFRESH_TOKEN_VALIDITY_DAYS))
                    .user(user)
                    .build();
            return refreshTokenRepository.save(refreshToken);
        }

        // Rotate the token and extend the expiry for the existing entry.
        existing.setToken(UUID.randomUUID().toString());
        existing.setExpiryDate(LocalDateTime.now().plusDays(REFRESH_TOKEN_VALIDITY_DAYS));
        return refreshTokenRepository.save(existing);
    }

    /**
     * Verifies a refresh token string: it must exist and not be expired.
     */
    public RefreshToken tokenVerify(String token) {
        RefreshToken refreshToken = refreshTokenRepository.findByToken(token)
                .orElseThrow(() -> new RuntimeException("Refresh token not found"));

        if (refreshToken.getExpiryDate().isBefore(LocalDateTime.now())) {
            throw new RuntimeException("Refresh token expired");
        }

        return refreshToken;
    }
}
