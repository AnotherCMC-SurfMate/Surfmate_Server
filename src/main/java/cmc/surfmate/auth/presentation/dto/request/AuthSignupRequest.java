package cmc.surfmate.auth.presentation.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * AuthSignupRequest.java
 *
 * @author jemlog
 */
@Data
@AllArgsConstructor
public class AuthSignupRequest {

    private String phNum;

    private String password;

    private String nickname;

    private String fcmToken;

}
