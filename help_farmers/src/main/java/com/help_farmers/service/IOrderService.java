package com.help_farmers.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.help_farmers.model.domain.LoginUser2;
import com.help_farmers.model.domain.Order;
import com.baomidou.mybatisplus.extension.service.IService;
import com.help_farmers.model.query.AccountQuery;
import com.help_farmers.model.query.AuditQuery;
import com.help_farmers.model.query.OrderQuery;
import com.help_farmers.model.query.SaveOrderQuery;
import com.help_farmers.model.vo.ListVo;
import com.help_farmers.model.vo.OrderVo;

import java.io.IOException;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 潘鑫
 * @since 2024-03-25
 */
public interface IOrderService extends IService<Order> {

    ListVo<OrderVo> orderList(OrderQuery orderQuery, LoginUser2 current) throws JsonProcessingException;

    void saveOrder(SaveOrderQuery saveOrderQuery, LoginUser2 current);

    void delById(Long[] ids);

    void audit(AuditQuery auditQuery, LoginUser2 current);

    void account(AccountQuery accountQuery, LoginUser2 current) throws IOException;

    void operate(AccountQuery accountQuery, LoginUser2 current) throws IOException;
}
