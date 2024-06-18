package com.help_farmers.common.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author: WZ
 * @Date: 2024/3/10 15:46
 * @Description:
 */

@Configuration
public class JsonUtil {


    @Bean
    public ObjectMapper getObjectMapper() {
        return new ObjectMapper();
    }
}
