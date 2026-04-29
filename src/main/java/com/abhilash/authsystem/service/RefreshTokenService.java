package com.abhilash.authsystem.service;

import com.abhilash.authsystem.entity.RefreshToken;
import com.abhilash.authsystem.entity.User;
import com.abhilash.authsystem.repository.RefreshTokenRepository;
import com.abhilash.authsystem.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.UUID;

@Service
@AllArgsConstructor
public class RefreshTokenService {

    private final RefreshTokenRepository repo;
    private final UserRepository userRepository;

    private static final long REFRESH_EXPIRY = 7 * 24 * 60 * 60 * 1000;

    public RefreshToken createRefreshToken(String email) {

        User user = userRepository.findUserByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        repo.deleteByUser(user);

        RefreshToken token = RefreshToken.builder()
                .user(user)
                .token(UUID.randomUUID().toString())
                .expiryDate(Instant.now().plusMillis(REFRESH_EXPIRY))
                .build();

        return repo.save(token);
    }

    public RefreshToken verifyToken(String token) {

        RefreshToken rt = repo.findByToken(token)
                .orElseThrow(() -> new RuntimeException("Invalid refresh token"));

        if (rt.getExpiryDate().isBefore(Instant.now())) {
            repo.delete(rt);
            throw new RuntimeException("Refresh token expired");
        }

        return rt;
    }

    public void deleteByUser(User user) {
        repo.deleteByUser(user);
    }
}
