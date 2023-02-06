package cmc.surfmate.auth.presentation.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * OAuthLoginResponseDto.java
 *
 * @author jemlog
 */
@Data
@AllArgsConstructor
public class OAuthLoginResponse{

    private AuthUserData user;
    private String token;
    private boolean isNewUser;


    public OAuthLoginResponse(AuthUserData user, boolean isNewUser) {
        this.user = user;
        this.isNewUser = isNewUser;
    }
}
