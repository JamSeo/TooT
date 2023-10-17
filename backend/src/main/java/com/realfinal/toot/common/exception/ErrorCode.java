package com.realfinal.toot.common.exception;

import org.springframework.http.HttpStatus;

public enum ErrorCode {

    /**
     * 비즈니스 에러 (확인 가능한 예외 상황)
     */
    /* 400 BAD_REQUEST : 잘못된 요청 */
    /* 401 UNAUTHORIZED : 인증되지 않은 사용자 */
    /* 404 NOT_FOUND : Resource를 찾을 수 없음 */
    /* 409 : CONFLICT : Resource의 현재 상태와 충돌. 보통 중복된 데이터 존재 */

    UNEXPECTED_ERROR(HttpStatus.BAD_REQUEST, "0"),
    
    CHAT_NOT_JSONFY(HttpStatus.CONFLICT, "1"),
    NOT_LOGINED(HttpStatus.BAD_REQUEST, "1000"),
    KIS_TOKEN_REQUEST_FAILED(HttpStatus.BAD_REQUEST, "881"),
    KIS_API_CALL_DENIED(HttpStatus.NOT_FOUND, "882"),
    KIS_API_CALL_TOO_MANY(HttpStatus.INTERNAL_SERVER_ERROR, "883"),

    NOT_PROVIDED_PROVIDER(HttpStatus.BAD_REQUEST, "999"),
    REDIS_NOT_SAVED(HttpStatus.CONFLICT, "998"), //레디스 저장 중 문제 발생.,
    TOKEN_CREATE_FAILED(HttpStatus.CONFLICT, "997"),
    TOKEN_NOT_VALID(HttpStatus.BAD_REQUEST, "996"),
    REDIS_NOT_DELETED(HttpStatus.BAD_REQUEST, "995"),
    MYSQL_NO_DATA(HttpStatus.BAD_REQUEST, "994"),
    MAPPING_PROBLEM(HttpStatus.CONFLICT, "993"),
    KAKAO_PARSE_DATA(HttpStatus.BAD_REQUEST, "992"),
    DATA_NOT_FOUND(HttpStatus.BAD_REQUEST, "991"),
    KAKAO_REQUEST_TOKEN(HttpStatus.BAD_REQUEST, "990"),
    TOKEN_NOT_IN_COOKIE(HttpStatus.BAD_REQUEST, "989"),
    DATABASE_EMPTY(HttpStatus.BAD_REQUEST, "799"),
    NO_DATA(HttpStatus.BAD_REQUEST, "798");

//    UNAUTHORIZED_ROLE(HttpStatus.UNAUTHORIZED, "현재 유저 권한으로는 접근할 수 없는 리소스 요청입니다."),

//    OUT_OF_POSSESSION(HttpStatus.UNAUTHORIZED, "해당 동작의 권한이 없습니다."),

//    JWT_TOKEN_EXPIRED_EXCEPTION(HttpStatus.BAD_REQUEST, "만료된 JWT 토큰입니다."),

//    INVAILD_JWT_TOKEN_EXCEPTION(HttpStatus.BAD_REQUEST, "유효하지 않은 Access Token입니다"),

//    REFRESH_TOKEN_NOT_MATCHED(HttpStatus.BAD_REQUEST, "유효하지 않은 Refresh Token입니다."),

//    PASSWORD_NOT_MATCHED(HttpStatus.BAD_REQUEST, "비밀번호가 일치하지 않습니다."),

//    NOT_ENOUGH_POINT(HttpStatus.BAD_REQUEST, "solve point가 부족합니다."),

//    NO_SUCH_DATA(HttpStatus.NOT_FOUND, "데이터가 존재하지 않습니다."),

//    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "회원을 찾을 수 없습니다."),

//    FAVORITE_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 즐겨찾기 정보를 찾을 수 없습니다."),

//    QUESTION_NOT_FOUND(HttpStatus.NOT_FOUND, "문제를 찾을 수 없습니다."),

//    ARTICLE_NOT_FOUND(HttpStatus.NOT_FOUND, "게시글을 찾을 수 없습니다."),

//    MASTER_CODE_NOT_FOUND(HttpStatus.NOT_FOUND, "부적절한 분류입니다."),

//    DUPLICATED_LOGIN_ID(HttpStatus.CONFLICT, "중복된 아이디 입니다."),

//    DUPLICATED_EMAIL(HttpStatus.CONFLICT, "중복된 이메일 입니다."),

//    DUPLICATED_NICKNAME(HttpStatus.CONFLICT, "중복된 닉네임 입니다."),

//    NO_IMAGE(HttpStatus.BAD_REQUEST, "요청에 이미지가 없습니다."),

//    IMAGE_UPLOAD_FAIL(HttpStatus.BAD_REQUEST, "이미지 업로드를 실패했습니다."),

    /**
     * 서버 에러 (서버 장애 상황)
     */
//    COMMON_SYSTEM_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "시스템 오류입니다. 잠시 후 다시 이용해주세요."),

//    FILE_IO_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "파일 입출력 오류입니다.");

    private final HttpStatus httpStatus;
    private final String message;

    ErrorCode(HttpStatus httpStatus, String message) {
        this.httpStatus = httpStatus;
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
