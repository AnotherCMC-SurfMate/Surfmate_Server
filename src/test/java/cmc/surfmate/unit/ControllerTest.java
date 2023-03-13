package cmc.surfmate.unit;

import cmc.surfmate.auth.application.impl.AuthService;
import cmc.surfmate.auth.application.impl.OAuthService;
import cmc.surfmate.auth.common.filter.TokenProvider;
import cmc.surfmate.auth.presentation.AuthController;
import cmc.surfmate.common.exception.GlobalExceptionHandler;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

/**
 * ControllerTest.java
 *
 * @author jemlog
 */
@WebMvcTest({
        AuthController.class,
        GlobalExceptionHandler.class
})
@ActiveProfiles("test")
public abstract class ControllerTest {

    @MockBean
    protected AuthService authService;

    @MockBean
    protected TokenProvider tokenProvider;

    @MockBean
    protected PasswordEncoder passwordEncoder;

    @MockBean
    protected OAuthService oAuthService;

    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected ObjectMapper objectMapper;
}
