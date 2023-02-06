package cmc.surfmate.auth.application.impl.dto.response;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * KakaoUserResponse.java
 *
 * @author jemlog
 */
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class KakaoUserResponse {
    private Long id;

    private LocalDateTime connected_at;
}
