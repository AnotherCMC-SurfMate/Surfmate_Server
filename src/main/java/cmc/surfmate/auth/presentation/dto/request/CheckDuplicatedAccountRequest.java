package cmc.surfmate.auth.presentation.dto.request;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * CheckDuplicatedAccountRequest.java
 *
 * @author jemlog
 */
@Data
@NoArgsConstructor
public class CheckDuplicatedAccountRequest {
  private String phNum;

}
