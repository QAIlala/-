package com.help_farmers.common.config;

import com.help_farmers.common.result.ResponseCode;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author: WZ
 * @Date: 2023/9/15 4:30
 * @Description:
 */

@Configuration
public class ThreadLocalConfig {

    @Bean
    public ThreadLocal<ResponseCode> getThreadLocal() {
        return new ThreadLocal<>();
    }
}
