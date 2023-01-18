package cmc.surfmate.common.util;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

import java.util.Objects;

/**
 * HeaderUtil.java
 *
 * @author jemlog
 */
@Slf4j
public class HeaderUtil {

    private final static String HEADER_AUTHORIZATION = "Authorization";
    private final static String TOKEN_PREFIX = "Bearer ";

    public static String getAccessToken(HttpServletRequest request)
    {
        String headerValue = request.getHeader(HEADER_AUTHORIZATION);

        // Authorization Header 내부에 값이 없다면 null 반환
        if(headerValue == null) {

            return null;

        }

        if(headerValue.startsWith(TOKEN_PREFIX))
        {
            String substring = headerValue.substring(TOKEN_PREFIX.length());
            return substring;
        }

        return null;
    }
}
