package com.gdgknu.Inveed.security.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gdgknu.Inveed.domain.user.User;
import com.gdgknu.Inveed.domain.user.UserRepository;
import com.gdgknu.Inveed.security.dto.LoginResDTO;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class OAuth2SuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
    private final JwtUtil jwtUtil;
    private final RefreshTokenService refreshTokenService;
    private final UserRepository userRepository;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
        String email = (String) oAuth2User.getAttributes().get("email");

        User user = userRepository.findByEmail(email).orElseThrow();

        // Creates access token and refresh token
        String accessToken = jwtUtil.createAccessToken(email);
        String refreshToken = jwtUtil.createRefreshToken(email);

        // Save refresh token
        refreshTokenService.saveRefreshToken(email, refreshToken);

        LoginResDTO loginResDTO = LoginResDTO.fromEntity(user, accessToken, refreshToken);

        // 세션에 토큰 저장
        request.getSession().setAttribute("accessToken", accessToken);
        request.getSession().setAttribute("refreshToken", refreshToken);

        response.sendRedirect("http://localhost:5500/");
    }
}