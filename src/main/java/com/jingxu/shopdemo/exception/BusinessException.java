package com.jingxu.shopdemo.exception;


import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author jingxu
 * @version 1.0.0
 * @since 2025/12/9
 */
@Data
@AllArgsConstructor
public class BusinessException extends RuntimeException {
    FailCodeEnums code;
    public BusinessException(String message) {
        super(message);
    }

    public BusinessException(FailCodeEnums code, String msg) {
        super(msg);
        this.code=code;
    }
}
