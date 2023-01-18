package cmc.surfmate.common.response;

import lombok.AllArgsConstructor;
import lombok.Getter;


/**
 * CommonResponse.java
 *
 * @author jemlog
 */
@AllArgsConstructor
@Getter
public class CommonResponse<T>{
    private int code;
    private String message;
    private T data;

    public CommonResponse(int code, String message)
    {
        this.code = code;
        this.message = message;
    }
}