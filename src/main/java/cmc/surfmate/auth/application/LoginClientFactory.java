package cmc.surfmate.auth.application;

import cmc.surfmate.common.enums.Provider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * LoginServiceFactory.java
 *
 * @author jemlog
 */
@Component
@RequiredArgsConstructor
public class LoginClientFactory {

    private final List<LoginClient> loginServiceList;
    private final Map<Provider, LoginClient> factoryCache;

    public LoginClient find(final Provider loginType) {

        LoginClient loginService = factoryCache.get(loginType);

        if (loginService != null) {
            return loginService;
        }

        loginService = loginServiceList.stream()
                .filter(v -> v.supports(loginType))
                .findFirst()
                .orElseThrow();

        factoryCache.put(loginType, loginService);
        return loginService;
    }
}
