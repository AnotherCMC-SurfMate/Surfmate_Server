package cmc.surfmate.auth.presentation.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * OAuthSignupResponse.java
 *
 * @author jemlog
 */
@Data
@AllArgsConstructor
public class OAuthSignupResponse {
    private AuthUserData user;
    private String token;
}
