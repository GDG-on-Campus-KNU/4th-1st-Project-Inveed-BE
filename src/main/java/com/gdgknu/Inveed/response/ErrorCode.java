package com.gdgknu.Inveed.response;



import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    /*
     * 400 BAD_REQUEST: 잘못된 요청
     */
    INVALID_INPUT_VALUE(HttpStatus.BAD_REQUEST, "잘못된 입력값입니다."),  // 예시
    REQUEST_FORMAT_ERROR(HttpStatus.BAD_REQUEST, "잘못된 요청 형식입니다."),


    /*
     * 401 UNAUTHORIZED: 권한 없음
     */
    UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "권한이 없습니다."),  // 예시
    NOT_LOGGED_IN(HttpStatus.UNAUTHORIZED, "로그인이 필요합니다."),
    TOKEN_EXPIRED(HttpStatus.UNAUTHORIZED, "토큰이 만료되었습니다."),
    LOGOUT(HttpStatus.UNAUTHORIZED, "로그아웃 상태입니다."),
    INVALID_TOKEN(HttpStatus.UNAUTHORIZED, "유효하지 않은 토큰입니다."),
    TOKEN_MISMATCHED(HttpStatus.UNAUTHORIZED, "잘못된 리프레쉬 토큰입니다."),
    REFRESH_TOKEN_NOT_FOUND(HttpStatus.NOT_FOUND, "리프레쉬 토큰을 찾을 수 없습니다."),
    KINVEST_TOKEN_NOT_FOUND(HttpStatus.NOT_FOUND, "한투 접근 토큰을 찾을 수 없습니다."),


    /*
     * 404 NOT_FOUND: 리소스를 찾을 수 없음
     */
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "사용자를 찾을 수 없습니다."),    // 예시
    HANDLER_NOT_FOUND(HttpStatus.NOT_FOUND, "존재하지 않는 핸들러입니다."),
    FAVORITE_STOCK_NOT_FOUND(HttpStatus.NOT_FOUND, "존재하지 않는 관심 종목입니다."),

    /*
     * 405 METHOD_NOT_ALLOWED: 허용되지 않은 Request Method 호출
     */
    METHOD_NOT_ALLOWED(HttpStatus.METHOD_NOT_ALLOWED, "허용되지 않은 메소드입니다."),  // 예시

    /*
     * 409 CONFLICT: 리소스 충돌 (이미 존재하는 리소스)
     */
    USER_ALREADY_EXISTS(HttpStatus.CONFLICT, "이미 존재하는 사용자입니다."),  // 예시
    FAVORITE_STOCK_ALREADY_EXISTS(HttpStatus.CONFLICT, "이미 등록된 관심 종목입니다."),

    /*
     * 500 INTERNAL_SERVER_ERROR: 내부 서버 오류 (Custom Exception 적용이 안되어 있는 경우)
     */
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "서버 오류입니다."),  // 예시
    KINVEST_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "한국투자증권으로부터 데이터를 받아오는 중 문제가 발생했습니다.");


    private final HttpStatus status;
    private final String message;
}
