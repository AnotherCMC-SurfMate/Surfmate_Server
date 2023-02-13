package cmc.surfmate.auth.application.impl;

import cmc.surfmate.auth.application.impl.dto.request.AuthLoginDto;
import cmc.surfmate.auth.application.impl.dto.request.AuthSignupDto;
import cmc.surfmate.auth.application.impl.dto.response.CheckDuplicatedAccountResponse;
import cmc.surfmate.auth.common.filter.TokenProvider;
import cmc.surfmate.auth.presentation.dto.response.AuthLoginResponse;
import cmc.surfmate.auth.presentation.dto.response.AuthSignupResponse;
import cmc.surfmate.common.enums.Provider;
import cmc.surfmate.common.enums.RoleType;
import cmc.surfmate.common.exception.GlobalBadRequestException;
import cmc.surfmate.user.domain.User;
import cmc.surfmate.user.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import java.util.Optional;
import static org.mockito.BDDMockito.*;


/**
 * AuthServiceTest.java
 *
 * @author jemlog
 */
@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
class AuthServiceTest {

  @Mock
  private UserRepository userRepository;

  @Mock
  private PasswordEncoder passwordEncoder;

  @Mock
  private TokenProvider tokenProvider;

  @InjectMocks
  private AuthService authService;

  private static String AUTH_SECRET = "Y21jMTF0aGFub3RoZXJjbWNzdXJmbWF0ZWNtYzExdGhhbm90aGVyY21jc3VyZm1hdGUfwegwegewfwwgwegwggsfgergtrr";
  private static Long TOKEN_VALIDATION = 1000L;

  @DisplayName("이미 존재하는 닉네임일 경우 중복 예외를 발생시킨다.")
  @Test
  void check_duplicated_nickname_exception()
  {
      // Given
      given(userRepository.findUserByNickname(anyString()))
              .willReturn(Optional.of(User.builder().nickname("jemin").build()));

      // Then
      Assertions.assertThrows(GlobalBadRequestException.class,()-> authService.checkDuplicatedNickname("jemin"));
      verify(userRepository,BDDMockito.times(1)).findUserByNickname(anyString());
  }


  @DisplayName("존재하지 않는 닉네임일 경우 아무 반환 값도 보내지 않는다.")
  @Test
  void check_duplicated_nickname_success() {
      // Given
      given(userRepository.findUserByNickname(anyString()))
              .willReturn(Optional.empty());

      // Then
      authService.checkDuplicatedNickname("jemin");
      verify(userRepository,BDDMockito.times(1)).findUserByNickname(anyString());
  }


  @DisplayName("1인 1계정 정책에 따라 중복되는 전화번호가 DB에 존재하면 isDuplicated 값을 true로 반환")
  @Test
  void check_unique_accounts_by_phNum_duplicated() {
      // Given
      User mockUser = User.builder().provider(Provider.NORMAL).build();
      given(userRepository.findUserByPhNum(anyString())).willReturn(Optional.of(mockUser));

      // When
      CheckDuplicatedAccountResponse mockResponse = authService.checkDuplicatedAccount(anyString());

      // Then
      org.assertj.core.api.Assertions.assertThat(mockResponse.getProvider()).isEqualTo(Provider.NORMAL);
      org.assertj.core.api.Assertions.assertThat(mockResponse.getIsDuplicated()).isTrue();
      verify(userRepository,BDDMockito.times(1)).findUserByPhNum(anyString());
  }


  @DisplayName("1인 1계정 정책에 따라 중복되는 전화번호가 DB에 존재하지 않는다면 isDuplicated 값을 false로 반환")
  @Test
  void check_unique_accounts_by_phNum_success() {
      // Given
      given(userRepository.findUserByPhNum(anyString())).willReturn(Optional.empty());

      // When
      CheckDuplicatedAccountResponse mockResponse = authService.checkDuplicatedAccount(anyString());

      // Then
      org.assertj.core.api.Assertions.assertThat(mockResponse.getProvider()).isNull();
      org.assertj.core.api.Assertions.assertThat(mockResponse.getIsDuplicated()).isFalse();
      verify(userRepository,BDDMockito.times(1)).findUserByPhNum(anyString());
  }


  @DisplayName("전화번호가 틀리면 NOT EXIST USER 예외를 발생시킨다.")
  @Test
  void invalid_phNum() {
      // Given
      given(userRepository.findUserByPhNum(anyString())).willReturn(Optional.empty());

      // Then
      Assertions.assertThrows(GlobalBadRequestException.class,() -> {
          authService.login(new AuthLoginDto("01027570146","password12","fasf-fdfewgw-sdf"));
      });
  }


  @DisplayName("비밀번호가 틀리면 INVALID_PASSWORD 예외를 발생시킨다.")
  @Test
  void invalid_password() {

      // Given
      User mockUser = User.builder().provider(Provider.NORMAL).build();
      lenient().when(userRepository.findUserByPhNum(anyString())).thenReturn(Optional.of(mockUser));
      lenient().when(passwordEncoder.matches(anyString(),anyString())).thenReturn(false);

      // Then
      Assertions.assertThrows(GlobalBadRequestException.class,() -> {
          authService.login(new AuthLoginDto("01027570146","password12","fasf-fdfewgw-sdf"));
      });
      verify(passwordEncoder,times(1)).matches(any(),any());
  }


  @DisplayName("로그인에 성공하면 토큰과 유저 정보를 반환한다.")
  @Test
  void login_success() {

      // Given
      User mockUser = User.builder().uid("testUid").provider(Provider.NORMAL).build();
      lenient().when(userRepository.findUserByPhNum(anyString())).thenReturn(Optional.of(mockUser));
      lenient().when(passwordEncoder.matches(any(),any())).thenReturn(true);
      lenient().when(tokenProvider.createToken(any(),any())).thenReturn("test_token_value");

      // When
      AuthLoginResponse result = authService.login(new AuthLoginDto("01027570146", "password12", "fasf-fdfewgw-sdf"));

      // Then
      org.assertj.core.api.Assertions.assertThat(result).isNotNull();
      org.assertj.core.api.Assertions.assertThat(result.getToken()).isEqualTo("test_token_value");
      org.assertj.core.api.Assertions.assertThat(result.getUser().getUid()).isEqualTo("testUid");
  }

  @DisplayName("요청값에 패스워드가 없으면 소셜 회원가입을 진행한다.")
  @Test
  public void select_normal_signup()
  {
      // Given
      AuthSignupDto mockDto = new AuthSignupDto("01027570146", "jemin", "testUid", Provider.KAKAO, "", "fcmToken");
      lenient().when(tokenProvider.createToken(any(),any())).thenReturn("test_token_value");

      // When
      AuthSignupResponse result = authService.signup(mockDto);

      // Then
      org.assertj.core.api.Assertions.assertThat(result.getUser().getProvider()).isEqualTo(Provider.KAKAO);
      org.assertj.core.api.Assertions.assertThat(result.getToken()).isEqualTo("test_token_value");
  }

  @DisplayName("요청값에 패스워드가 있으면 일반 회원가입을 진행한다.")
  @Test
  public void select_social_signup()
  {
      // Given
      AuthSignupDto mockDto = new AuthSignupDto("01027570146", "jemin", "testUid", null, "password12", "fcmToken");
      lenient().when(tokenProvider.createToken(any(),any())).thenReturn("test_token_value");

      // When
      AuthSignupResponse result = authService.signup(mockDto);

      // Then
      org.assertj.core.api.Assertions.assertThat(result.getUser().getProvider()).isEqualTo(Provider.NORMAL);
      org.assertj.core.api.Assertions.assertThat(result.getToken()).isEqualTo("test_token_value");
  }


  @DisplayName("소셜 로그인 ID와 권한을 제공하면 문자열 토큰을 만들어낸다.")
  @Test
  void create_token()
  {
      // Given
      TokenProvider tokenProvider = new TokenProvider(AUTH_SECRET, TOKEN_VALIDATION);

      // When
      String token = tokenProvider.createToken("socialLoginToken", RoleType.USER);

      // Then
      org.assertj.core.api.Assertions.assertThat(token).isNotNull();
  }
}