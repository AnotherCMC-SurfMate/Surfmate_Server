package cmc.surfmate.auth.presentation.dto.request;

import cmc.surfmate.auth.application.impl.dto.request.AuthSignupDto;
import cmc.surfmate.common.enums.Gender;
import cmc.surfmate.common.enums.Provider;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Pattern;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.annotation.Nullable;
import java.time.LocalDate;

/**
 * CommonAuthRequest.java
 *
 * @author jemlog
 */
@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CommonSignupRequest {

    private String phNum;

    @Length(min = 2, max = 15,
            message = "닉네임은 2자리 이상 15자리 이하입니다.")
    private String nickname;

    private String username; // 실명 입력

    private String uid;

    private Provider provider;

    @Pattern(regexp = "^(?=.*\\d)(?=.*[a-zA-Z])[0-9a-zA-Z]{8,}",
            message = "비밀번호는 영문과 숫자 조합으로 8자리 이상 가능합니다.")
    @Nullable
    private String password;

    private Gender gender;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
    private LocalDate birthDay;

    private String fcmToken;

    public AuthSignupDto toServiceDto()
    {
        return new AuthSignupDto(phNum,nickname, username ,uid, provider,password,fcmToken,gender,birthDay);
    }
}
