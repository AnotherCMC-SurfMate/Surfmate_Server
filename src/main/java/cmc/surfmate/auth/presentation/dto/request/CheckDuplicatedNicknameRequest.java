package cmc.surfmate.auth.presentation.dto.request;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * CheckDuplicatedNicknameRequest.java
 *
 * @author jemlog
 */
@Data
@NoArgsConstructor
public class CheckDuplicatedNicknameRequest {
    private String nickname;
}
