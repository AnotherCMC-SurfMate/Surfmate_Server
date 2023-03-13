package cmc.surfmate.common.exception;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * GlobalExceptionResponse.java
 *
 * @author jemlog
 */
@AllArgsConstructor
@Data
public class GlobalExceptionResponse{

    private String code;
    private String message;
}
