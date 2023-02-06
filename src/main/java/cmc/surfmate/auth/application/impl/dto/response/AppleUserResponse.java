package cmc.surfmate.auth.application.impl.dto;

import lombok.*;

import java.util.List;
import java.util.Optional;

/**
 * AppleKeyResponse.java
 *
 * @author jemlog
 */
@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AppleUserResponse {
    private List<Key> keys;

    @Getter
    @Setter
    public static class Key {
        private String kty;
        private String kid;
        private String use;
        private String alg;
        private String n;
        private String e;
    }

    public Optional<Key> getMatchedKeyBy(String kid, String alg) {
        return this.keys.stream()
                .filter(key -> key.getKid().equals(kid) && key.getAlg().equals(alg))
                .findFirst();
    }
}
