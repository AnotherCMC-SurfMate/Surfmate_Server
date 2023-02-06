package cmc.surfmate.auth.presentation.dto.assembler;

import cmc.surfmate.auth.presentation.dto.request.AuthSignupRequest;
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

    public static User createNormalLoginUser(AuthSignupRequest authSignupRequest, PasswordEncoder passwordEncoder)
    {
        String encodePassword = passwordEncoder.encode(authSignupRequest.getPassword());

        return User.builder()
                .phNum(authSignupRequest.getPhNum()) // 비밀번호 추가
                .password(encodePassword)
                .roleType(RoleType.USER)
                .fcmToken(authSignupRequest.getFcmToken())
                .uid(UUID.randomUUID().toString())
                .provider(Provider.NORMAL)
                .nickname(authSignupRequest.getNickname())
                .build();
    }

    public static AuthSignupResponse authSignupResponse(String accessToken, User user)
    {
        return new AuthSignupResponse(new AuthUserData(user.getId(), user.getUid(), user.getProvider(), user.getPhNum(), user.getNickname()), accessToken);
    }


//    public static AuthRequestForSignupDto authRequestForSignupDto(AuthRequestForSignup authRequestForSignup)
//    {
//        return new AuthRequestForSignupDto(authRequestForSignup.getEmail(),
//                authRequestForSignup.getPassword(),
//                authRequestForSignup.getNickname(),
//                authRequestForSignup.getAgeGroup(),
//                authRequestForSignup.getGender(),
//                authRequestForSignup.getFcmToken(),
//                authRequestForSignup.getProfileImage());
//    }
//
//    public static CommonResponse authUserDataResponse(User user)
//    {
//        return new CommonResponse(200,"인증 유저 정보",new AuthmeWrappingDto(new AuthUserData(user.getUserSeq(),
//
//                user.getUserId(),
//                user.getProvider(),
//                user.getEmail(),
//                user.getNickname(),
//                user.getProfileImage(),
//                user.getGender(),
//                user.getAgeGroup())
//        ));
//    }

    public static AuthLoginResponse authLoginResponse(String accessToken, User user)
    {
        return new AuthLoginResponse(new AuthUserData(user.getId(),
                user.getUid(),
                user.getProvider(),
                user.getPhNum(),
                user.getNickname()),
                accessToken);
    }

    public static OAuthSignupResponse oauthSignupResponse(String accessToken, User user)
    {
        return new OAuthSignupResponse(new AuthUserData(user.getId(),
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
                    new OAuthLoginResponse(AuthUserData.builder().uid(user.getUid()).build(),isNewUser)
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