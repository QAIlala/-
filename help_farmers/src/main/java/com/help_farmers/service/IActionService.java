package com.help_farmers.service;

import com.help_farmers.common.result.Result;
import com.help_farmers.model.domain.Action;
import com.baomidou.mybatisplus.extension.service.IService;
import com.help_farmers.model.dto.GrantDTO;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 潘鑫
 * @since 2024-03-12
 */
public interface IActionService extends IService<Action> {

    Result queryAllActions(Integer roleId);

    void grant(GrantDTO grantDTO);
}
