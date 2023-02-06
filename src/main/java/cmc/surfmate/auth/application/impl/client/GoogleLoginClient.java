package cmc.surfmate.auth.application.impl.client;

import cmc.surfmate.auth.application.LoginClient;
import cmc.surfmate.auth.application.impl.dto.response.GoogleUserResponse;
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
public class GoogleLoginClient implements LoginClient {
    private final WebClient webClient;

    @Override
    public boolean supports(Provider provider) {
        return provider == Provider.GOOGLE;
    }

    @Override
    public User getUserData(String accessToken) {

        GoogleUserResponse googleUserResponse = webClient.get()
                .uri("https://oauth2.googleapis.com/tokeninfo", builder -> builder.queryParam("id_token", accessToken).build())
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, response -> Mono.error(new IllegalAccessException()))
                .onStatus(HttpStatusCode::is5xxServerError, response -> Mono.error(new IllegalAccessException()))
                .bodyToMono(GoogleUserResponse.class)
                .block();

        return User.builder()
                .uid(googleUserResponse.getSub())
                .provider(Provider.GOOGLE)
                .password("NO_PASSWORD")
                .roleType(RoleType.USER).build();



    }
}
