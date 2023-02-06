package cmc.surfmate.auth.presentation.dto;

import cmc.surfmate.user.domain.User;
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
