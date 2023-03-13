package cmc.surfmate.auth.presentation.dto.request;

import cmc.surfmate.auth.application.impl.dto.request.AuthLoginDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import org.hibernate.validator.constraints.Length;

/**
 * AuthLoginRequest.java
 *
 * @author jemlog
 */
@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AuthLoginRequest {

    @Schema(example = "01027579923")
    private String phNum;

    @Length(min = 8)
    @Schema(example = "password12@")
    private String password;
    @Schema(description = "인증 유저 디바이스 fcm token")
    private String fcmToken;

    public AuthLoginDto toServiceDto()
    {
        return new AuthLoginDto(phNum,password,fcmToken);
    }

}