package cmc.surfmate.auth.presentation.dto.request;

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
