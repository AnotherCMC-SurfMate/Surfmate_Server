package cmc.surfmate.auth.application.impl;

import cmc.surfmate.auth.application.LoginClient;
import cmc.surfmate.auth.application.LoginClientFactory;
import cmc.surfmate.auth.application.impl.dto.response.OAuthLoginResponseDto;
import cmc.surfmate.auth.filter.TokenProvider;
import cmc.surfmate.auth.presentation.dto.assembler.AuthAssembler;
import cmc.surfmate.auth.presentation.dto.request.OAuthLoginRequest;
import cmc.surfmate.auth.presentation.dto.request.OAuthSignupRequest;
import cmc.surfmate.auth.presentation.dto.response.OAuthSignupResponse;
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

        Optional<User> findUser = userRepository.findById(socialUser.getId());

        if (findUser.isPresent()) {

            User user = findUser.get();

            if (user.getPhNum() != null) {
                String accessToken = getToken(socialUser.getUid(), user.getRoleType());
                user.addFCMToken(oAuthLoginRequest.getFcmToken());
                return new OAuthLoginResponseDto( accessToken, false,user);
            } else {
                return new OAuthLoginResponseDto(null,true,User.builder().uid(socialUser.getUid()).build());

            }
        }

        socialUser.addFCMToken(oAuthLoginRequest.getFcmToken());
        userRepository.save(socialUser);

        return new OAuthLoginResponseDto(null,true,User.builder().uid(socialUser.getUid()).build());

    }


    @Transactional
    public OAuthSignupResponse signup(OAuthSignupRequest oAuthSignupRequest) {

        User user = userRepository.findById(oAuthSignupRequest.getUserId()).orElseThrow(()->{throw new IllegalArgumentException();});

        user.updateUserForSignup(oAuthSignupRequest.getPhNum(),oAuthSignupRequest.getNickname());

        String token = getToken(user.getUid(),user.getRoleType());

        return AuthAssembler.oauthSignupResponse(token,user);

    }

    private String getToken(String userId, RoleType role) {
        return tokenProvider.createToken(userId, role);
    }
}