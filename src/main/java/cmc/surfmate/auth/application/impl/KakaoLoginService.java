package cmc.surfmate.auth.application.impl;

import cmc.surfmate.auth.application.LoginClient;
import cmc.surfmate.auth.application.impl.dto.GoogleUserResponse;
import cmc.surfmate.auth.infrastructure.feignClient.GoogleOAuthOpenFeign;
import cmc.surfmate.common.enums.Provider;
import cmc.surfmate.common.enums.Role;
import cmc.surfmate.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * GoogleLoginService.java
 *
 * @author jemlog
 */
@Service
@RequiredArgsConstructor
public class KakaoLoginService implements LoginClient {
    private final GoogleOAuthOpenFeign googleOAuthOpenFeign;
    @Override
    public boolean supports(Provider loginType) {
        return loginType == Provider.KAKAO;
    }

    @Override
    public User getUserData(String accessToken) {

        GoogleUserResponse response = googleOAuthOpenFeign.getUserData(accessToken);
        return User.builder()
                .uid(response.getSub())
                .provider(Provider.GOOGLE)
                .password("NO_PASSWORD")
                .role(Role.USER).build();
    }
}
