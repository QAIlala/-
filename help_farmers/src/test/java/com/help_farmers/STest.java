package com.help_farmers;

import com.help_farmers.common.utils.SnowFlakeUtil;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

/**
 * @Author: WZ
 * @Date: 2024/3/25 23:32
 * @Description:
 */

@SpringBootTest
public class STest {

    @Resource
    private SnowFlakeUtil snowFlakeUtil;


    @Test
    public void test01() {
        System.out.println(snowFlakeUtil.nextId());
        System.out.println(System.currentTimeMillis());
    }
}
