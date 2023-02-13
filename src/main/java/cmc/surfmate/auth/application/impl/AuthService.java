package cmc.surfmate.auth.application.impl;

import cmc.surfmate.auth.application.impl.dto.request.AuthLoginDto;
import cmc.surfmate.auth.application.impl.dto.request.AuthSignupDto;
import cmc.surfmate.auth.application.impl.dto.response.CheckDuplicatedAccountResponse;
import cmc.surfmate.auth.common.filter.TokenProvider;
import cmc.surfmate.auth.presentation.dto.assembler.AuthAssembler;
import cmc.surfmate.auth.presentation.dto.request.CommonSignupRequest;
import cmc.surfmate.auth.presentation.dto.response.AuthLoginResponse;
import cmc.surfmate.auth.presentation.dto.response.AuthSignupResponse;
import cmc.surfmate.common.enums.Provider;
import cmc.surfmate.common.enums.RoleType;
import cmc.surfmate.common.exception.GlobalBadRequestException;
import cmc.surfmate.user.domain.User;
import cmc.surfmate.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static cmc.surfmate.common.exception.ExceptionCodeAndDetails.*;

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


    public AuthSignupResponse signup(AuthSignupDto authSignupDto)
    {
        if(authSignupDto.getPassword().isBlank())
        {
            return doSocialSignup(authSignupDto);
        }
        else{
            return doNormalSignup(authSignupDto);
        }
    }

    private AuthSignupResponse doNormalSignup(AuthSignupDto authSignupDto) {

        User user = AuthAssembler.createNormalLoginUser(authSignupDto,passwordEncoder);
        userRepository.save(user);
        String accessToken = getToken(user.getUid(),user.getRoleType());
        return AuthAssembler.authSignupResponse(accessToken, user);
    }


    private AuthSignupResponse doSocialSignup(AuthSignupDto authSignupDto) {

        User user = AuthAssembler.createSocialLoginUser(authSignupDto);
        userRepository.save(user);
        String token = getToken(user.getUid(),user.getRoleType());
        return AuthAssembler.oauthSignupResponse(token, user);
    }


    public AuthLoginResponse login(AuthLoginDto authLoginDto)
    {

        User user = userRepository.findUserByPhNum(authLoginDto.getPhNum()).orElseThrow(()->
        {throw new GlobalBadRequestException(NOT_EXIST_USER);
        });

        if(!passwordEncoder.matches(authLoginDto.getPassword(), user.getPassword())) {
            throw new GlobalBadRequestException(INVALID_PASSWORD);
        }

        user.addFCMToken(authLoginDto.getFcmToken());

        String accessToken = getToken(user.getUid(), user.getRoleType());

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
        Optional<User> userByNickname = userRepository.findUserByNickname(nickname);
        if(userByNickname.isPresent())
        {
            throw new GlobalBadRequestException(DUPLICATED_NICKNAME);
        }
    }

    private String getToken(String userId, RoleType role) {
        return tokenProvider.createToken(userId, role);
    }


}
