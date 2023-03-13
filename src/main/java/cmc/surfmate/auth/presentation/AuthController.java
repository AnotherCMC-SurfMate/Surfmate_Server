package cmc.surfmate.auth.presentation;

import cmc.surfmate.auth.application.impl.AuthService;
import cmc.surfmate.auth.application.impl.OAuthService;
import cmc.surfmate.auth.application.impl.dto.response.OAuthLoginResponseDto;
import cmc.surfmate.auth.presentation.dto.response.AuthLoginResponse;
import cmc.surfmate.auth.presentation.dto.response.AuthSignupResponse;
import cmc.surfmate.auth.application.impl.dto.response.CheckDuplicatedAccountResponse;
import cmc.surfmate.auth.presentation.dto.assembler.AuthAssembler;
import cmc.surfmate.auth.presentation.dto.request.*;
import cmc.surfmate.common.response.CommonResponse;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * AuthController.java
 *
 * @author jemlog
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;
    private final OAuthService oAuthService;

    @Operation(summary = "소셜 로그인",description = "소셜 로그인 타입에 따라 로그인 방식을 분기")
    @PostMapping("/social")
    public ResponseEntity<CommonResponse> socialLogin(@Valid @RequestBody OAuthLoginRequest oAuthLoginRequest)
    {
        OAuthLoginResponseDto oAuthLoginResponse = oAuthService.login(oAuthLoginRequest);
        return ResponseEntity.ok(AuthAssembler.departNewUser(oAuthLoginResponse.getAccessToken(),
                                                             oAuthLoginResponse.getIsNewUser(),
                                                             oAuthLoginResponse.getUser()));

    }

    @Operation(summary = "일반 로그인")
    @PostMapping("/login")
    public ResponseEntity<CommonResponse> normalLogin(@Valid @RequestBody AuthLoginRequest authLoginRequest)
    {
        AuthLoginResponse authLoginResponse = authService.login(authLoginRequest.toServiceDto());
        return ResponseEntity.ok(new CommonResponse<>(200,"성공",authLoginResponse));
    }


    @Operation(summary = "회원가입", description = "전화번호, 비밀번호, FCM TOKEN, 닉네임을 입력받음")
    @PostMapping("/signup")
    public ResponseEntity<CommonResponse> normalSignup(@Valid @RequestBody CommonSignupRequest commonSignupRequest)
    {
        AuthSignupResponse authSignupResponse = authService.signup(commonSignupRequest.toServiceDto());
        return ResponseEntity.ok(new CommonResponse(200, "성공",authSignupResponse));
    }


    @Operation(summary = "1인 1계정 체크", description = "전화번호를 기준으로 가입된 계정이 있는지 체크")
    @PostMapping("/check/account")
    public ResponseEntity<CommonResponse> checkDuplicatedAccount(@RequestBody CheckDuplicatedAccountRequest checkDuplicatedAccountRequest)
    {
        CheckDuplicatedAccountResponse response = authService.checkDuplicatedAccount(checkDuplicatedAccountRequest.getPhNum());
        return ResponseEntity.ok(new CommonResponse(200,"성공",response));
    }


    @Operation(summary = "닉네임 중복 체크", description = "닉네임 설정 시 중복 여부 판단")
    @PostMapping("/check/nickname")
    public ResponseEntity<CommonResponse> checkDuplicatedNickname(@RequestBody CheckDuplicatedNicknameRequest checkDuplicatedNicknameRequest)
    {
        authService.checkDuplicatedNickname(checkDuplicatedNicknameRequest.getNickname());
        return ResponseEntity.ok(new CommonResponse(200,"성공"));
    }

}
