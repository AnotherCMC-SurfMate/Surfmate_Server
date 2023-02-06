package cmc.surfmate.auth.presentation.dto.request;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * OAuthSignupRequest.java
 *
 * @author jemlog
 */

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OAuthSignupRequest {
    private String phNum;
    private String nickname;
    private Long userId;
}
