package cmc.surfmate.auth.common.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * RestAuthenticationEntryPoint.java
 *
 * @author jemlog
 */
@Slf4j
@Component
public class RestAuthenticationEntryPoint implements AuthenticationEntryPoint {


    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {

        final Map<String, Object> body = new HashMap<>();

        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(HttpStatus.UNAUTHORIZED.value());

        body.put("code", "1005");
        body.put("message", "미인증 유저");

        final ObjectMapper mapper = new ObjectMapper();

        mapper.writeValue(response.getOutputStream(), body);
    }


//    @Override
//    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException {
//
//        final Map<String, Object> body = new HashMap<>();
//
//        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
//        response.setStatus(HttpStatus.UNAUTHORIZED.value());
//
//        body.put("code", "1005");
//        body.put("message", "인증되지 않은 유저입니다.");
//
//        final ObjectMapper mapper = new ObjectMapper();
//
//        mapper.writeValue(response.getOutputStream(), body);
//
//    }
}