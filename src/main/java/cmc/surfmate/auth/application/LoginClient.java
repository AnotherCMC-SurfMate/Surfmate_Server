package cmc.surfmate.auth.application;

import cmc.surfmate.common.enums.Provider;
import cmc.surfmate.user.domain.User;

/**
 * LoginService.java
 *
 * @author jemlog
 */
public interface LoginClient {
    boolean supports(Provider loginType);
    User getUserData(String accessToken);
}
