package com.gdgknu.Inveed.security.controller;

import com.gdgknu.Inveed.security.dto.LoginResDTO;
import com.gdgknu.Inveed.security.dto.LogoutReqDTO;
import com.gdgknu.Inveed.security.dto.ReissueReqDTO;
import com.gdgknu.Inveed.security.service.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/refresh")
    public ResponseEntity<LoginResDTO> refreshAccessToken(@RequestBody ReissueReqDTO reissueReqDTO) {
        LoginResDTO authResDTO = authService.refreshAccessToken(reissueReqDTO);
        return ResponseEntity.ok(authResDTO);
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(@RequestBody LogoutReqDTO logoutReqDTO) {
        authService.logout(logoutReqDTO);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/auth-info")
    public ResponseEntity<Map<String, String>> getAuthInfo(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        String accessToken = (String) session.getAttribute("accessToken");
        String refreshToken = (String) session.getAttribute("refreshToken");

        if (accessToken == null || refreshToken == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        Map<String, String> tokens = new HashMap<>();
        tokens.put("accessToken", accessToken);
        tokens.put("refreshToken", refreshToken);

        return ResponseEntity.ok(tokens);
    }
}
