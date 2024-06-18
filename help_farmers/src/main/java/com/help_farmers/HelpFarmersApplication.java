package com.help_farmers;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @Author: 潘鑫
 * @Date: 2023/5/31 11:32
 * @Description:
 */
@SpringBootApplication
@MapperScan("com.help_farmers.mapper")
public class HelpFarmersApplication {
    public static void main(String[] args) {

        SpringApplication.run(HelpFarmersApplication.class,args);

    }
}
