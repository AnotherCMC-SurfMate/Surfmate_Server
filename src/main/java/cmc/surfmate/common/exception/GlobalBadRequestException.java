package cmc.surfmate.common.exception;

import lombok.Getter;

/**
 * GlobalBadRequestException.java
 *
 * @author jemlog
 */
@Getter
public class GlobalBadRequestException extends RuntimeException{

    private final ExceptionCodeAndDetails exceptionCodeAndDetails;

    public GlobalBadRequestException(ExceptionCodeAndDetails exceptionCodeAndDetails)
    {
        super(exceptionCodeAndDetails.getMessage());
        this.exceptionCodeAndDetails = exceptionCodeAndDetails;
    }
}
