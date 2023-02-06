package cmc.surfmate.auth.presentation.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

/**
 * AuthLoginResponse.java
 *
 * @author jemlog
 */
@Data
public class AuthLoginResponse {
    private AuthUserData user;
    private String token;

    @Builder
    public AuthLoginResponse(AuthUserData user, String token) {
        this.user = user;
        this.token = token;
    }
}


