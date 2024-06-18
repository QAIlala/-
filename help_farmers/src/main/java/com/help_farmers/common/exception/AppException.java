package com.help_farmers.common.exception;


import com.help_farmers.common.result.ResponseCode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 作者：wz
 * 时间：2023/5/24 14:39
 * 描述：业务异常类： 和我们的业务是相关的
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AppException extends RuntimeException {

    private String code;
    private String message;

    public AppException(ResponseCode responseCode) {
        this.code = responseCode.getCode();
        this.message = responseCode.getMessage();
    }
}
