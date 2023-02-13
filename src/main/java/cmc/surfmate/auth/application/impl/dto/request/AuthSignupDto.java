package cmc.surfmate.auth.application.impl.dto.request;

import cmc.surfmate.common.enums.Provider;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * AuthSignupDto.java
 *
 * @author jemlog
 */

@Data
@AllArgsConstructor
public class AuthSignupDto {

    private String phNum;

    // 공통
    private String nickname;

    // 소셜 로그인 용
    private String uid;

    private Provider provider;

    // 일반 로그인 용
    private String password;

    // 이거는 일반 로그인 용
    private String fcmToken;
}
