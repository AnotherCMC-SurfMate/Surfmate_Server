package cmc.surfmate.auth.application;

import cmc.surfmate.common.enums.Provider;
import cmc.surfmate.user.domain.User;

/**
 * LoginClient.java
 *
 * @author jemlog
 */
public interface LoginClient {

    boolean supports(Provider provider);
    User getUserData(String accessToken);
}
