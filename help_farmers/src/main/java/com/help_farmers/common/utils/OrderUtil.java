package com.help_farmers.common.utils;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author: WZ
 * @Date: 2024/3/25 23:30
 * @Description:
 */

@Configuration
public class OrderUtil {

    @Bean
    public SnowFlakeUtil getSnowFlakeUtil() {
        return new SnowFlakeUtil(1L,1L);
    }
}
