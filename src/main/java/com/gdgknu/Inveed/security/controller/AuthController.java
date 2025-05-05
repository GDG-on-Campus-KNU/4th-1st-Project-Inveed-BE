package com.gdgknu.Inveed.security.controller;

import com.gdgknu.Inveed.response.ResponseUtil;
import com.gdgknu.Inveed.response.SuccessCode;
import com.gdgknu.Inveed.response.SuccessResponse;
import com.gdgknu.Inveed.security.dto.KInvestLoginReq;
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
    public ResponseEntity<SuccessResponse<LoginResDTO>> refreshAccessToken(@RequestBody ReissueReqDTO reissueReqDTO) {
        LoginResDTO loginResDTO = authService.refreshAccessToken(reissueReqDTO);
        return ResponseUtil.buildSuccessResponse(SuccessCode.REFRESH_TOKEN_SUCCESS, loginResDTO);
    }

    @PostMapping("/logout")
    public ResponseEntity<SuccessResponse<Object>> logout(HttpServletResponse response, @RequestAttribute("accessToken") String accessToken) {
        authService.logout(response, accessToken);
        return ResponseUtil.buildSuccessResponse(SuccessCode.LOGOUT_SUCCESS);
    }

    @GetMapping("/login-info")
    public ResponseEntity<SuccessResponse<LoginResDTO>> getLoginInfo(@CookieValue(value = "accessToken", required = false) String accessToken) {
        if (accessToken == null)
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

        LoginResDTO loginResDTO = authService.getLoginInfo(accessToken);
        return ResponseUtil.buildSuccessResponse(SuccessCode.LOGIN_SUCCESS, loginResDTO);
    }

    @PostMapping("/kinvest-login")
    public ResponseEntity<SuccessResponse<Object>> kinvestLogin(@RequestAttribute("accessToken") String accessToken, @RequestBody KInvestLoginReq kInvestLoginReq) {
        authService.kinvestLogin(accessToken, kInvestLoginReq);
        return ResponseUtil.buildSuccessResponse(SuccessCode.KINVEST_LOGIN_SUCCESS);
    }
}
