package cmc.surfmate.auth.application.impl;

import cmc.surfmate.auth.application.LoginClient;

import cmc.surfmate.auth.application.impl.dto.NaverUserResponse;

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
public class NaverLoginClient implements LoginClient {
    private final WebClient webClient;

    @Override
    public boolean supports(Provider provider) {
        return provider == Provider.NAVER;
    }

    @Override
    public User getUserData(String accessToken) {

        NaverUserResponse naverUserResponse = webClient.get()
                .uri("https://openapi.naver.com/v1/nid/me")
                .headers(h -> h.setBearerAuth(accessToken))
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, clientResponse -> Mono.error(new IllegalAccessException()))
                .onStatus(HttpStatusCode::is5xxServerError, clientResponse -> Mono.error(new IllegalAccessException()))
                .bodyToMono(NaverUserResponse.class)
                .block();

        return User.builder()
                .uid(naverUserResponse.getResponse().getId())
                .provider(Provider.NAVER)
                .roleType(RoleType.USER)
                .password("NO_PASSWORD")
                .build();
    }
}
