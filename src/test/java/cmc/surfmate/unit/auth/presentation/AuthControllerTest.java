package cmc.surfmate.unit.auth.presentation;

import cmc.surfmate.auth.application.impl.dto.response.CheckDuplicatedAccountResponse;
import cmc.surfmate.auth.presentation.dto.request.CheckDuplicatedAccountRequest;
import cmc.surfmate.auth.presentation.dto.request.CheckDuplicatedNicknameRequest;
import cmc.surfmate.common.enums.Provider;
import cmc.surfmate.common.exception.ExceptionCodeAndDetails;
import cmc.surfmate.common.exception.GlobalBadRequestException;
import cmc.surfmate.unit.ControllerTest;
import cmc.surfmate.user.domain.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * AuthControllerTest.java
 *
 * @author jemlog
 */

class AuthControllerTest extends ControllerTest {

    @DisplayName("전화번호 중복 체크를 하면 중복 여부와 로그인 타입이 반환된다.")
    @WithMockUser
    @Test
    void check_duplicated_account() throws Exception {

        CheckDuplicatedAccountRequest mockDto = new CheckDuplicatedAccountRequest();
        mockDto.setPhNum("01027570146");

        given(authService.checkDuplicatedAccount(anyString())).willReturn(new CheckDuplicatedAccountResponse(Provider.NORMAL,false));


        ResultActions perform = mockMvc.perform(post("/auth/check/account").contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(mockDto))
                        .with(csrf()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.isDuplicated").value(false));
    }

    @DisplayName("닉네임 중복 체크 시 중복이 존재하면 HTTP status 400, 에러코드 E0004, 중복된 닉네임이라는 메세지가 반환된다.")
    @WithMockUser
    @Test
    void check_duplicated_nickname() throws Exception {
        CheckDuplicatedNicknameRequest mockDto = new CheckDuplicatedNicknameRequest();
        mockDto.setNickname("jemin");

        doThrow(new GlobalBadRequestException(ExceptionCodeAndDetails.DUPLICATED_NICKNAME)).when(authService).checkDuplicatedNickname(anyString());

        ResultActions perform = mockMvc.perform(post("/auth/check/nickname").contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(mockDto))
                        .with(csrf()))
                .andDo(print())
                .andExpect(status().is4xxClientError());

    }


}