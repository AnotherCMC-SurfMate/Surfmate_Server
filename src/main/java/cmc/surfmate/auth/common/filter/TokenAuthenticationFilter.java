package cmc.surfmate.auth.common.filter;

import cmc.surfmate.common.util.HeaderUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * TokenAuthenticationFilter.java
 *
 * @author jemlog
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class TokenAuthenticationFilter extends OncePerRequestFilter {

    private final TokenProvider tokenProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)  throws ServletException, IOException {

        String jwt = HeaderUtil.getAccessToken(request);

        if (StringUtils.hasText(jwt) && tokenProvider.validateToken(jwt)) {
            // 토큰에서 유저네임, 권한을 뽑아 스프링 시큐리티 유저를 만들어 Authentication 반환
            Authentication authentication = tokenProvider.getAuthentication(jwt);
            // 해당 스프링 시큐리티 유저를 시큐리티 건텍스트에 저장, 즉 디비를 거치지 않음
            SecurityContextHolder.getContext().setAuthentication(authentication);

            logger.debug("Security Context에  인증 정보를 저장했습니다");

        } else {

            logger.debug("유효한 JWT 토큰이 없습니다");

        }

        filterChain.doFilter(request, response);
    }

}
