package com.help_farmers.common.utils;


import com.help_farmers.common.exception.AppException;
import com.help_farmers.common.result.ResponseCode;

public class AssertUtil {


    public  static void isTrue(Boolean flag, ResponseCode responseCode){
        if(flag){
            throw new AppException(responseCode);
        }
    }

}
