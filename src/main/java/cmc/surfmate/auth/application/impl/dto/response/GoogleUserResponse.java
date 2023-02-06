package cmc.surfmate.auth.application.impl.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * GoogleUserResponse.java
 *
 * @author jemlog
 */
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class GoogleUserResponse {

    private String sub;
    private String email;
    private Boolean email_verified;

}
