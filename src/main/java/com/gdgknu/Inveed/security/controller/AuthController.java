package com.gdgknu.Inveed.security.controller;

import com.gdgknu.Inveed.security.dto.LoginResDTO;
import com.gdgknu.Inveed.security.dto.ReissueReqDTO;
import com.gdgknu.Inveed.security.service.AuthService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/refresh")
    public ResponseEntity<LoginResDTO> refreshAccessToken(@RequestBody ReissueReqDTO reissueReqDTO) {
        LoginResDTO authResDTO = authService.refreshAccessToken(reissueReqDTO);
        return ResponseEntity.ok(authResDTO);
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpServletResponse response, @RequestAttribute("accessToken") String accessToken) {
        authService.logout(response, accessToken);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/login-info")
    public ResponseEntity<LoginResDTO> getLoginInfo(@CookieValue(value = "accessToken", required = false) String accessToken) {
        if (accessToken == null)
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

        LoginResDTO loginResDTO = authService.getLoginInfo(accessToken);
        return ResponseEntity.ok(loginResDTO);
    }
}
