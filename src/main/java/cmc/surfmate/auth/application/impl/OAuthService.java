package cmc.surfmate.auth.application.impl;

import cmc.surfmate.auth.application.LoginClient;
import cmc.surfmate.auth.application.LoginClientFactory;
import cmc.surfmate.auth.application.impl.dto.response.OAuthLoginResponseDto;
import cmc.surfmate.auth.common.filter.TokenProvider;
import cmc.surfmate.auth.presentation.dto.request.OAuthLoginRequest;
import cmc.surfmate.common.enums.RoleType;
import cmc.surfmate.user.domain.User;
import cmc.surfmate.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * OAuthService.java
 *
 * @author jemlog
 */

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class OAuthService {

    private final UserRepository userRepository;
    private final LoginClientFactory loginClientFactory;
    private final TokenProvider tokenProvider;

    @Transactional
    public OAuthLoginResponseDto login(OAuthLoginRequest oAuthLoginRequest) {

        LoginClient loginClient = loginClientFactory.find(oAuthLoginRequest.getProvider());
        User socialUser = loginClient.getUserData(oAuthLoginRequest.getAccessToken());

        /*
        유저가 존재하면 인증 성공
        유저가 존재하지 않으면 회원가입으로 넘어감
         */
        Optional<User> findUser = userRepository.findById(socialUser.getId());

        // 기존 유저가 존재하면 로그인 성공
        if(findUser.isPresent())
        {
            String accessToken = getToken(socialUser.getUid(), socialUser.getRoleType());
            findUser.get().addFCMToken(socialUser.getFcmToken());
            return new OAuthLoginResponseDto(accessToken, false, findUser.get());
        }

        // 존재하지 않는다면 회원가입으로 이동
        socialUser.addFCMToken(oAuthLoginRequest.getFcmToken());
        return new OAuthLoginResponseDto(null,true,socialUser);
//            if (user.getPhNum() != null) {
//                String accessToken = getToken(socialUser.getUid(), user.getRoleType());
//                user.addFCMToken(oAuthLoginRequest.getFcmToken());
//                return new OAuthLoginResponseDto( accessToken, false,user);
//            } else {
//                // 아직 회원가입을 하지 않았을때!
//                return new OAuthLoginResponseDto(null,true,User.builder().uid(socialUser.getUid()).build());
//            }

    }

    private String getToken(String userId, RoleType role) {
        return tokenProvider.createToken(userId, role);
    }
}