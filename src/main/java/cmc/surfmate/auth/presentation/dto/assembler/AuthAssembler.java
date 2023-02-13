package cmc.surfmate.auth.presentation.dto.assembler;

import cmc.surfmate.auth.application.impl.dto.request.AuthSignupDto;
import cmc.surfmate.auth.presentation.dto.request.CommonSignupRequest;
import cmc.surfmate.auth.presentation.dto.response.*;
import cmc.surfmate.common.enums.Provider;
import cmc.surfmate.common.enums.RoleType;
import cmc.surfmate.common.response.CommonResponse;
import cmc.surfmate.user.domain.User;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.UUID;

/**
 * AuthAssembler.java
 *
 * @author jemlog
 */
public class AuthAssembler {

    public static User createNormalLoginUser(AuthSignupDto authSignupDto, PasswordEncoder passwordEncoder)
    {
        String encodePassword = passwordEncoder.encode(authSignupDto.getPassword());

        return User.builder()
                .phNum(authSignupDto.getPhNum()) // 비밀번호 추가
                .password(encodePassword)
                .roleType(RoleType.USER)
                .fcmToken(authSignupDto.getFcmToken())
                .uid(UUID.randomUUID().toString())
                .provider(Provider.NORMAL)
                .nickname(authSignupDto.getNickname())
                .build();
    }

    public static User createSocialLoginUser(AuthSignupDto authSignupDto)
    {
        return User.builder()
                .phNum(authSignupDto.getPhNum())
                .password("NO_PASSWORD")
                .roleType(RoleType.USER)
                .fcmToken(authSignupDto.getFcmToken())
                .uid(authSignupDto.getUid())
                .provider(authSignupDto.getProvider())
                .nickname(authSignupDto.getNickname())
                .build();
    }

    public static AuthSignupResponse authSignupResponse(String accessToken, User user)
    {
        return new AuthSignupResponse(new AuthUserData(user.getId(), user.getUid(), user.getProvider(), user.getPhNum(), user.getNickname()), accessToken);
    }


    public static AuthLoginResponse authLoginResponse(String accessToken, User user)
    {
        return new AuthLoginResponse(new AuthUserData(user.getId(),
                user.getUid(),
                user.getProvider(),
                user.getPhNum(),
                user.getNickname()),
                accessToken);
    }

    public static AuthSignupResponse oauthSignupResponse(String accessToken, User user)
    {
        return new AuthSignupResponse(new AuthUserData(user.getId(),
                user.getUid(),
                user.getProvider(),
                user.getPhNum(),
                user.getNickname()),
                accessToken);
    }

    public static CommonResponse departNewUser(String token, boolean isNewUser, User user)
    {
        if(isNewUser == true)
        {
            return new CommonResponse(200,
                    "회원가입 필요",
                    new OAuthLoginResponse(new AuthUserData(
                            user.getId(), // 없을 예정
                            user.getUid(), // 소셜 로그인 ID
                            user.getProvider(), // 소셜 로그인 type
                            user.getPhNum(),  // phNum -> 없을 예정
                            user.getNickname()  // nickname  -> 없을 예정
                    ),isNewUser)
            );
        }
        else{
            return new CommonResponse(200,
                    "성공",
                    new OAuthLoginResponse(new AuthUserData(user.getId(),
                            user.getUid(),
                            user.getProvider(),
                            user.getPhNum(),
                            user.getNickname()),
                            token,
                            isNewUser)
            );
        }
    }
}