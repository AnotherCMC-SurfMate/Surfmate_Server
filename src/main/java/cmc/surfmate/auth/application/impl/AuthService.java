package cmc.surfmate.auth.application.impl;

import cmc.surfmate.auth.application.impl.dto.response.CheckDuplicatedAccountResponse;
import cmc.surfmate.auth.filter.TokenProvider;
import cmc.surfmate.auth.presentation.dto.assembler.AuthAssembler;
import cmc.surfmate.auth.presentation.dto.request.AuthLoginRequest;
import cmc.surfmate.auth.presentation.dto.request.AuthSignupRequest;
import cmc.surfmate.auth.presentation.dto.response.AuthLoginResponse;
import cmc.surfmate.auth.presentation.dto.response.AuthSignupResponse;
import cmc.surfmate.common.enums.Provider;
import cmc.surfmate.user.domain.User;
import cmc.surfmate.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * AuthService.java
 *
 * @author jemlog
 */
@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenProvider tokenProvider;


    public AuthSignupResponse signup(AuthSignupRequest authSignupRequest)
    {
        User user = AuthAssembler.createNormalLoginUser(authSignupRequest,passwordEncoder);

        userRepository.save(user);

        String accessToken = tokenProvider.createToken(user.getUid(),user.getRoleType());
        return AuthAssembler.authSignupResponse(accessToken,user);
    }




    public AuthLoginResponse login(AuthLoginRequest authLoginRequest)
    {

        User user = userRepository.findUserByPhNum(authLoginRequest.getPhNum()).orElseThrow(()->{throw new IllegalArgumentException();});

        if(!passwordEncoder.matches(authLoginRequest.getPassword(), user.getPassword())) {
            throw new IllegalArgumentException();
        }

        user.addFCMToken(authLoginRequest.getFcmToken());
        String accessToken = tokenProvider.createToken(user.getUid(),user.getRoleType());

        return AuthAssembler.authLoginResponse(accessToken,user);
    }

    public CheckDuplicatedAccountResponse checkDuplicatedAccount(String phNum)
    {
        Optional<User> userByPhNum = userRepository.findUserByPhNum(phNum);

        if(userByPhNum.isPresent())
        {
            Provider provider = userByPhNum.get().getProvider();
            return new CheckDuplicatedAccountResponse(provider,true);
        }
        else{
            return new CheckDuplicatedAccountResponse(null,false);
        }
    }

    public void checkDuplicatedNickname(String nickname)
    {
        userRepository.findUserByNickname(nickname).orElseThrow(() -> new IllegalArgumentException());
    }

//    public void logout(String uid, String accessToken)
//    {
//        authenticatedUserChecker.checkAuthenticatedUserExist(uid);
//        redisTemplate.opsForValue().set(accessToken,"blackList");
//    }

//    private void withdraw(String uid,String accessToken) {
//
//        User user = authenticatedUserChecker.checkAuthenticatedUserExist(uid);
//        userRepository.delete(user);
//        redisTemplate.opsForValue().set(accessToken,"blackList");
//    }
}
