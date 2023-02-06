package cmc.surfmate.auth.presentation.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * CheckDuplicatedAccountRequest.java
 *
 * @author jemlog
 */
@Data
@AllArgsConstructor
public class CheckDuplicatedAccountRequest {

  private String phNum;
}
