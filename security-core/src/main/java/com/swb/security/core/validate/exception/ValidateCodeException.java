package com.swb.security.core.validate.exception;

import org.springframework.security.core.AuthenticationException;

/**
 * @author swb
 * 时间  2020-04-05 17:57
 * 文件  ValidateCodeException
 */
public class ValidateCodeException extends AuthenticationException {
    public ValidateCodeException(String msg) {
        super(msg);
    }
}
