package cmc.surfmate.auth.presentation.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * CheckDuplicatedNicknameRequest.java
 *
 * @author jemlog
 */
@Data
@AllArgsConstructor
public class CheckDuplicatedNicknameRequest {
    private String nickname;
}
