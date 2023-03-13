package cmc.surfmate.common.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * ExceptionCodeAndDetails.java
 *
 * @author jemlog
 */
@AllArgsConstructor
@Getter
public enum ExceptionCodeAndDetails {


    NOT_FOUND_ERROR_CODE("E0001", "발생한 에러의 에러코드를 찾을 수 없습니다."),
    BAD_REQUEST("E0002", "잘못된 요청입니다."),

    NOT_FOUND_API("E0003", "해당 경로에 대한 응답 API를 찾을 수 없습니다."),
    DUPLICATED_NICKNAME("E0004", "중복된 닉네임입니다"),
    NOT_EXIST_USER("E0005","존재하지 않는 유저입니다"),
    INVALID_PHONE_NUMBER("E0006","아이디 및 비밀번호를 확인해주세요."),
    INVALID_PASSWORD("E0007","아이디 및 비밀번호를 확인해주세요.");
    private final String code;
    private final String message;
}