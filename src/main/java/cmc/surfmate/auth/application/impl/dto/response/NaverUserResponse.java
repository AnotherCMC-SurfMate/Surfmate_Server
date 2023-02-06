package cmc.surfmate.auth.application.impl.dto;

import lombok.*;

/**
 * NaverUserResponse.java
 *
 * @author jemlog
 */
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class NaverUserResponse {

    private String resultcode;
    private String message;
    private Response response;

    @Data
    @NoArgsConstructor
    public class Response{

        private String id;
        private String email;
        private String nickname;



    }
}
