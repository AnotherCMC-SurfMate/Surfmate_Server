package cmc.surfmate.auth.infrastructure.feignClient;

import cmc.surfmate.auth.application.impl.dto.GoogleUserResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * OAuthLoginOpenFeign.java
 *
 * @author jemlog
 */
@FeignClient(name = "AppleOAuthOpenFeign",url = "https://oauth2.googleapis.com/tokeninfo")
public interface AppleOAuthOpenFeign {
    @GetMapping
    GoogleUserResponse getUserData(@RequestParam String accessToken);
}
