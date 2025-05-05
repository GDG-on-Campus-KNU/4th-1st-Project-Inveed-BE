package com.gdgknu.Inveed.security.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gdgknu.Inveed.domain.user.User;
import com.gdgknu.Inveed.domain.user.UserRepository;
import com.gdgknu.Inveed.response.CustomException;
import com.gdgknu.Inveed.response.ErrorCode;
import com.gdgknu.Inveed.security.dto.KInvestLoginReq;
import com.gdgknu.Inveed.security.dto.LoginResDTO;
import com.gdgknu.Inveed.security.dto.ReissueReqDTO;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Service
@Transactional
@RequiredArgsConstructor
public class AuthService {

    private final RefreshTokenService refreshTokenService;
    private final KInvestTokenService kInvestTokenService;
    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;

    final String KINVEST_BASE_URL = "https://openapivts.koreainvestment.com:29443";
    final String KINVEST_ISSUE_URL = "/oauth2/tokenP";
    final String KINVEST_REVOKE_URL = "/oauth2/revokeP";

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
        kInvestTokenService.deleteTokens(email);

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

    public void kinvestLogin(String accessToken, KInvestLoginReq kInvestLoginReq) {
        String email = jwtUtil.extractEmail(accessToken);

        RestTemplate restTemplate = new RestTemplate();

        // Setup body
        Map<String, String> jsonBody = new HashMap<>();
        jsonBody.put("grant_type", "client_credentials");
        jsonBody.put("appkey", kInvestLoginReq.appKey());
        jsonBody.put("appsecret", kInvestLoginReq.appSecret());

        // Setup header
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.valueOf("application/json; charset=UTF-8"));

        HttpEntity<Map<String, String>> entity = new HttpEntity<>(jsonBody, headers);

        ResponseEntity<String> response = restTemplate.exchange(
                KINVEST_BASE_URL + KINVEST_ISSUE_URL,
                HttpMethod.POST,
                entity,
                String.class
        );

        if (response.getStatusCode() != HttpStatus.OK)
            throw new CustomException(ErrorCode.KINVEST_SERVER_ERROR);

        kInvestTokenService.saveKInvestToken(email, extractAccessToken(response));

    }

    public String extractAccessToken(ResponseEntity<String> response) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode root = objectMapper.readTree(response.getBody());
            return root.path("access_token").asText();
        } catch (Exception e) {
            throw new CustomException(ErrorCode.KINVEST_SERVER_ERROR);
        }
    }
}