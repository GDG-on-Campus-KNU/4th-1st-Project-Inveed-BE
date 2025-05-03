package com.gdgknu.Inveed.security.service;

import com.gdgknu.Inveed.domain.user.User;
import com.gdgknu.Inveed.domain.user.UserRepository;
import com.gdgknu.Inveed.response.CustomException;
import com.gdgknu.Inveed.response.ErrorCode;
import com.gdgknu.Inveed.security.dto.LoginResDTO;
import com.gdgknu.Inveed.security.dto.ReissueReqDTO;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
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
        String email = jwtUtil.extractEmail(reissueReqDTO.refreshToken());
        User user = userRepository.findByEmail(email).orElseThrow(()->new CustomException(ErrorCode.USER_NOT_FOUND));
        String storedRefreshToken = refreshTokenService.getRefreshToken(email);

        if (!reissueReqDTO.refreshToken().equals(storedRefreshToken)) {
            throw new CustomException(ErrorCode.TOKEN_MISMATCHED);
        }

        String newAccessToken = jwtUtil.createAccessToken(email);
        String newRefreshToken = jwtUtil.createRefreshToken(email);

        return LoginResDTO.fromEntity(user, newAccessToken, newRefreshToken);
    }

    public void logout(HttpServletResponse response, String accessToken) {
        String email = jwtUtil.extractEmail(accessToken);
        refreshTokenService.deleteTokens(email);

        Cookie accessTokenCookie = new Cookie("accessToken", null);
        accessTokenCookie.setHttpOnly(true);
        accessTokenCookie.setPath("/");
        accessTokenCookie.setMaxAge(0);  // Expiring accessToken in cookie
        response.addCookie(accessTokenCookie);
    }

    public LoginResDTO getLoginInfo(String accessToken) {
        String email = jwtUtil.extractEmail(accessToken);
        User user = userRepository.findByEmail(email).orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));
        String storedRefreshToken = refreshTokenService.getRefreshToken(email);

        return LoginResDTO.fromEntity(user, accessToken, storedRefreshToken);
    }
}
