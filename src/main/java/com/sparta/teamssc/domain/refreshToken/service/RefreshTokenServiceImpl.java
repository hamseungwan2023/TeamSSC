package com.sparta.teamssc.domain.refreshToken.service;

import com.sparta.teamssc.domain.refreshToken.entity.RefreshToken;
import com.sparta.teamssc.domain.refreshToken.repository.RefreshTokenRepository;
import com.sparta.teamssc.domain.user.entity.User;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RefreshTokenServiceImpl {
    private final RefreshTokenRepository refreshTokenRepository;

    @Transactional
    public RefreshToken createOrUpdateRefreshToken(User user, String token) {
        RefreshToken refreshToken = refreshTokenRepository.findByRefreshToken(token)
                .orElse(new RefreshToken(user, token));
        refreshToken.updateRefreshToken(token);
        return refreshTokenRepository.save(refreshToken);
    }
}