package cmc.surfmate.auth.presentation.dto;

import lombok.Builder;

/**
 * AuthLoginResponse.java
 *
 * @author jemlog
 */
public class AuthLoginResponse {
    private AuthUserData user;
    private String token;

    @Builder
    public AuthLoginResponse(AuthUserData user, String token) {
        this.user = user;
        this.token = token;
    }
}


