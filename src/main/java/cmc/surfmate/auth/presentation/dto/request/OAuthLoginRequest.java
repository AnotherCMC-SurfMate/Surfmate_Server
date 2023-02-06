package cmc.surfmate.auth.presentation.dto.request;

import cmc.surfmate.common.enums.Provider;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * AuthRequest.java
 *
 * @author jemlog
 */
@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OAuthLoginRequest {

    @Schema(description = "리소스 서버 접근용 액세스 토큰")
    private String accessToken;

    @Schema(description = "소셜 로그인 서비스 제공자",example = "KAKAO")
    private Provider provider;

    @Schema(description = "인증 유저 디바이스 FCM 토큰")
    private String fcmToken;
}