package com.overcomingroom.bellbell.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {
    // Member
    MEMBER_INVALID(HttpStatus.BAD_REQUEST, "멤버 정보가 유효하지 않습니다."),
    MEMBER_NOT_FOUND(HttpStatus.NOT_FOUND, "멤버를 찾을 수 없습니다."),

    // OAuth
    LOGIN_ERROR(HttpStatus.BAD_REQUEST, "로그인 오류"),
    ACCESS_DENIED(HttpStatus.BAD_REQUEST, "접근 권한이 없습니다."),

    // Weather
    LOCATION_INFORMATION_NOT_FOUND(HttpStatus.NOT_FOUND, "위치 정보를 찾을 수 없습니다."),
    MEMBER_LOCATION_INFORMATION_NOT_FOUND(HttpStatus.NOT_FOUND, "유저가 기 저장한 위치 정보가 없습니다."),
    API_CALL_BAD_REQUEST(HttpStatus.BAD_REQUEST, "잘못된 응답 형식입니다."),
    WEATHER_API_RES_RESULT_IS_EMPTY(HttpStatus.NOT_FOUND, "날씨 데이터가 존재하지 않습니다."),

    // UserNotification
    INVALID_INPUT_VALUE(HttpStatus.BAD_REQUEST, "입력값이 유효하지 않습니다."),
    NOT_EXISTS_USER_NOTIFICATION(HttpStatus.BAD_REQUEST, "사용자 알림이 존재하지 않습니다."),

    // Auth
    JWT_VALUE_IS_EMPTY(HttpStatus.UNAUTHORIZED, "JWT를 요청 헤더에 넣어주세요."),

    // BasicNotification
    BASIC_NOTIFICATION_IS_EMPTY(HttpStatus.NOT_FOUND, "기본 알림 데이터가 존재하지 않습니다.");

    private final HttpStatus status;
    private final String msg;

    ErrorCode(HttpStatus status, String msg) {
        this.status = status;
        this.msg = msg;
    }

}
