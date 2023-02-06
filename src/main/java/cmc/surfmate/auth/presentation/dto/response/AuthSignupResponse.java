package cmc.surfmate.auth.presentation.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * AuthSignupResponse.java
 *
 * @author jemlog
 */
@Data
@AllArgsConstructor
public class AuthSignupResponse {
    private AuthUserData user;
    private String token;
}
