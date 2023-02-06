package cmc.surfmate.auth.application.impl.dto.response;

import cmc.surfmate.common.enums.Provider;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * CheckDuplicatedAccountResponse.java
 *
 * @author jemlog
 */
@Data
@AllArgsConstructor
public class CheckDuplicatedAccountResponse {

    private Provider provider;
    private Boolean isDuplicated;

}
