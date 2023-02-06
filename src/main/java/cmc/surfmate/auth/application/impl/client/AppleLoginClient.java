package cmc.surfmate.auth.application.impl;

import cmc.surfmate.auth.application.LoginClient;
import cmc.surfmate.auth.application.impl.dto.AppleUserResponse;
import cmc.surfmate.common.enums.Provider;
import cmc.surfmate.common.enums.RoleType;
import cmc.surfmate.user.domain.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.RSAPublicKeySpec;
import java.util.Base64;
import java.util.Map;

/**
 * GoogleLoginService.java
 *
 * @author jemlog
 */

@Service
@RequiredArgsConstructor
public class AppleLoginClient implements LoginClient {

    private final WebClient webClient;

    @Override
    public boolean supports(Provider provider) {
        return provider == Provider.APPLE;
    }

    @Override
    public User getUserData(String accessToken) {

        AppleUserResponse response = webClient.get()
                .uri("https://appleid.apple.com/auth/keys")
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, clientResponse -> Mono.error(new IllegalAccessException()))
                .onStatus(HttpStatusCode::is5xxServerError, clientResponse -> Mono.error(new IllegalAccessException()))
                .bodyToMono(AppleUserResponse.class)
                .block();

        try {
            return getUserFromToken(accessToken, response);
        }
        catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            throw new IllegalArgumentException();
        }
        catch (InvalidKeySpecException e) {
            e.printStackTrace();
            throw new IllegalArgumentException();
        } catch (JsonMappingException e) {
            e.printStackTrace();
            throw new IllegalArgumentException();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            throw new IllegalArgumentException();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            throw new IllegalArgumentException();
        } catch (MalformedJwtException e) {
            e.printStackTrace();
            throw new IllegalArgumentException();
        }
        catch (ExpiredJwtException e) {
            e.printStackTrace();
            throw new IllegalArgumentException();
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new IllegalArgumentException();
        }
    }


    private static User getUserFromToken(String accessToken, AppleUserResponse response) throws JsonProcessingException, UnsupportedEncodingException, IllegalAccessException, NoSuchAlgorithmException, InvalidKeySpecException {
        String headerOfIdentityToken = accessToken.substring(0, accessToken.indexOf("."));

        Map<String, String> header = new ObjectMapper().readValue(new String(Base64.getDecoder().decode(headerOfIdentityToken),
                "UTF-8"), Map.class);

        AppleUserResponse.Key key = response.getMatchedKeyBy(header.get("kid"), header.get("alg"))
                .orElseThrow(() -> new IllegalAccessException());

        byte[] nBytes = Base64.getUrlDecoder().decode(key.getN());
        byte[] eBytes = Base64.getUrlDecoder().decode(key.getE());

        BigInteger n = new BigInteger(1, nBytes);
        BigInteger e = new BigInteger(1, eBytes);

        RSAPublicKeySpec publicKeySpec = new RSAPublicKeySpec(n, e);
        KeyFactory keyFactory = KeyFactory.getInstance(key.getKty());
        PublicKey publicKey = keyFactory.generatePublic(publicKeySpec);

        Claims body = Jwts.parser().setSigningKey(publicKey).parseClaimsJws(accessToken).getBody();

        return User.builder()
                .uid(body.getSubject())
                .password("NO_PASSWORD")
                .provider(Provider.APPLE)
                .roleType(RoleType.USER)
                .build();
    }

}