package cmc.surfmate.unit.auth.application;

import cmc.surfmate.auth.application.LoginClient;
import cmc.surfmate.auth.application.LoginClientFactory;
import cmc.surfmate.auth.application.impl.client.AppleLoginClient;
import cmc.surfmate.auth.application.impl.client.GoogleLoginClient;
import cmc.surfmate.auth.application.impl.client.KakaoLoginClient;
import cmc.surfmate.auth.application.impl.client.NaverLoginClient;
import cmc.surfmate.common.enums.Provider;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * LoginClientFactoryTest.java
 *
 * @author jemlog
 */
class LoginClientFactoryTest {

    @Mock
    WebClient webClient;

    @DisplayName("소셜 로그인 Provider를 제공하면 해당하는 통신 클라이언트가 반환된다.")
    @Test
    public void select_socialLogin_client()
    {
        // Given
        List<LoginClient> mockLoginClientList = List.of(new GoogleLoginClient(webClient), new KakaoLoginClient(webClient), new NaverLoginClient(webClient), new AppleLoginClient(webClient));
        LoginClientFactory mockLoginClientFactory = new LoginClientFactory(mockLoginClientList);

        // When
        LoginClient loginClientKakao = mockLoginClientFactory.find(Provider.KAKAO);
        LoginClient loginClientApple = mockLoginClientFactory.find(Provider.APPLE);

        // Then
        Assertions.assertThat(loginClientKakao).isInstanceOf(KakaoLoginClient.class);
        Assertions.assertThat(loginClientApple).isInstanceOf(AppleLoginClient.class);

    }

}