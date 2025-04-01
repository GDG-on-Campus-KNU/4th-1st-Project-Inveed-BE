package com.gdgknu.Inveed.response;


import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum SuccessCode {
    /**
     * 200 OK: 성공 (GET, PUT, DELETE)
     */
    LOGIN_SUCCESS(HttpStatus.OK, "로그인 성공"),
    LOGOUT_SUCCESS(HttpStatus.OK, "로그아웃 성공"),


    /**
     * 201 CREATED: 새로운 리소스 생성 성공 (POST)
     */
    BUILDING_POST_SUCCESS(HttpStatus.CREATED, "건물 생성 성공");


    private final HttpStatus status;
    private final String message;
}
