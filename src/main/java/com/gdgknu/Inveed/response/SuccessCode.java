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
    REFRESH_TOKEN_SUCCESS(HttpStatus.OK, "토큰 재발급 성공"),
    KINVEST_LOGIN_SUCCESS(HttpStatus.OK, "한국투자증권 토큰 발급 성공"),

    STOCK_RANK_VOL_SUCCESS(HttpStatus.OK, "거래량 조회 성공"),
    STOCK_RANK_PBMN_SUCCESS(HttpStatus.OK, "거래대금 조회 성공"),
    STOCK_RANK_GROWTH_SUCCESS(HttpStatus.OK, "거래증가율 조회 성공"),
    STOCK_RANK_TRUNOVER_SUCCESS(HttpStatus.OK, "거래회전율 조회 성공"),

    FAVORITE_STOCK_READ_SUCCESS(HttpStatus.OK, "관심 종목 조회 성공"),
    FAVORITE_STOCK_DELETE_SUCCESS(HttpStatus.OK, "관심 종목 삭제 성공"),

    LOG_SEARCH_SUCCESS(HttpStatus.OK, "검색 성공"),
    LOG_KEYWORD_SUCCESS(HttpStatus.OK, "검색어 로그 성공"),
    TOP10_KEYWORD_SUCCESS(HttpStatus.OK, "상위 10개 검색어 조회 성공"),

    /**
     * 201 CREATED: 새로운 리소스 생성 성공 (POST)
     */
    BUILDING_POST_SUCCESS(HttpStatus.CREATED, "건물 생성 성공"),
    FAVORITE_STOCK_POST_SUCCESS(HttpStatus.CREATED, "관심 종목 추가 성공");


    private final HttpStatus status;
    private final String message;
}
