package cmc.surfmate.auth.application.impl.client;

import cmc.surfmate.auth.application.LoginClient;
import cmc.surfmate.auth.application.impl.dto.response.KakaoUserResponse;
import cmc.surfmate.common.enums.Provider;
import cmc.surfmate.common.enums.RoleType;
import cmc.surfmate.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

/**
 * GoogleLoginService.java
 *
 * @author jemlog
 */
@Service
@RequiredArgsConstructor
public class KakaoLoginClient implements LoginClient {
    private final WebClient webClient;


    @Override
    public boolean supports(Provider provider) {
        return provider == Provider.KAKAO;
    }

    @Override
    public User getUserData(String accessToken) {

        KakaoUserResponse kakaoUserResponse = webClient.get()
                .uri("https://kapi.kakao.com/v2/user/me")
                .headers(h -> h.setBearerAuth(accessToken))
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, clientResponse -> Mono.error(new IllegalAccessException()))
                .onStatus(HttpStatusCode::is5xxServerError, clientResponse -> Mono.error(new IllegalAccessException()))
                .bodyToMono(KakaoUserResponse.class)
                .block();

        return User.builder()
                .uid(String.valueOf(kakaoUserResponse.getId()))
                .provider(Provider.KAKAO)
                .roleType(RoleType.USER)
                .password("NO_PASSWORD")
                .build();
    }
}
