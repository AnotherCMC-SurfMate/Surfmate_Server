package cmc.surfmate.auth.presentation.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * OAuthSignupRequest.java
 *
 * @author jemlog
 */

@Data
@AllArgsConstructor
public class OAuthSignupRequest {
    private String phNum;
    private String nickname;
    private Long userId;
}
