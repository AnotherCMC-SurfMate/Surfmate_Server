package cmc.surfmate.auth.presentation.dto.request;

import cmc.surfmate.auth.application.impl.dto.request.AuthSignupDto;
import cmc.surfmate.common.enums.Provider;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * CommonAuthRequest.java
 *
 * @author jemlog
 */
@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CommonSignupRequest {

    // 공통
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

    public AuthSignupDto toServiceDto()
    {
        return new AuthSignupDto(phNum,nickname, uid, provider,password,fcmToken);
    }
}
