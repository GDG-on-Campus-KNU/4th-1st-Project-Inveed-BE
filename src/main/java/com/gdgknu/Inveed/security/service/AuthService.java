package com.gdgknu.Inveed.security.service;

import com.gdgknu.Inveed.domain.user.User;
import com.gdgknu.Inveed.domain.user.UserRepository;
import com.gdgknu.Inveed.security.dto.LoginResDTO;
import com.gdgknu.Inveed.security.dto.LogoutReqDTO;
import com.gdgknu.Inveed.security.dto.ReissueReqDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class AuthService {

    private final RefreshTokenService refreshTokenService;
    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;

    public LoginResDTO refreshAccessToken(ReissueReqDTO reissueReqDTO) {

        if (!jwtUtil.isTokenValid(reissueReqDTO.refreshToken())) {
            throw new RuntimeException("Invalid refresh token");
        }

        String email = jwtUtil.parseToken(reissueReqDTO.refreshToken()).getSubject();
        System.out.println(email);
        User user = userRepository.findByEmail(email).orElseThrow();
        String storedRefreshToken = refreshTokenService.getRefreshToken(email);

        if (!reissueReqDTO.refreshToken().equals(storedRefreshToken)) {
            throw new RuntimeException("Refresh Token mismatch");
        }

        String newAccessToken = jwtUtil.createAccessToken(email);
        String newRefreshToken = jwtUtil.createRefreshToken(email);

        return LoginResDTO.fromEntity(user, newAccessToken, newRefreshToken);
    }

    public void logout(LogoutReqDTO logoutReqDTO) {
        if (!jwtUtil.isTokenValid(logoutReqDTO.accessToken()))
            throw new RuntimeException("Invalid refresh token");

        String email = jwtUtil.parseToken(logoutReqDTO.accessToken()).getSubject();
        refreshTokenService.deleteTokens(email);
    }

    public LoginResDTO getLoginInfo(String accessToken, String refreshToken) {
        String email = jwtUtil.extractEmail(accessToken);
        User user = userRepository.findByEmail(email).orElseThrow();
        return LoginResDTO.fromEntity(user, accessToken, refreshToken);
    }
}
