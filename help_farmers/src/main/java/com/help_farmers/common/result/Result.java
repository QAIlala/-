package com.help_farmers.common.result;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 作者：wz
 * 时间：2023/5/25 11:09
 * 描述：
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Result {

    private String code;
    private String message;
    private Object data;

    public Result(ResponseCode rc, Object data) {
        this.code = rc.getCode();
        this.message = rc.getMessage();
        this.data = data;
    }

    public Result(String code, String message) {
        this.code = code;
        this.message = message;
    }
}
