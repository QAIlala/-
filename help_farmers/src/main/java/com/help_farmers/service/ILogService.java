package com.help_farmers.service;

import com.help_farmers.model.domain.Log;
import com.baomidou.mybatisplus.extension.service.IService;
import com.help_farmers.model.domain.LoginUser2;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 潘鑫
 * @since 2024-03-25
 */
public interface ILogService extends IService<Log> {

    void delById(Integer[] logIds, LoginUser2 current);
}
