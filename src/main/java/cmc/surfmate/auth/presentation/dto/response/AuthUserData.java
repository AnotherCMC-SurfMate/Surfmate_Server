package cmc.surfmate.auth.presentation.dto;

import cmc.surfmate.common.enums.Provider;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

/**
 * AccessTokenUserData.java
 *
 * @author jemlog
 */
@Data
@AllArgsConstructor
@Builder
public class AuthUserData {
    private Long userId;
    private String uid;
    private Provider provider;
    private String phNum;
    private String nickname;

}
