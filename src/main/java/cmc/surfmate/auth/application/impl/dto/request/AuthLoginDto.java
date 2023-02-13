package cmc.surfmate.auth.application.impl.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

/**
 * AuthLoginDto.java
 *
 * @author jemlog
 */
@Data
@AllArgsConstructor
public class AuthLoginDto {

    private String phNum;

    private String password;

    private String fcmToken;
}
