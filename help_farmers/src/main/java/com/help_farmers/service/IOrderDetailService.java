package com.help_farmers.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.help_farmers.model.domain.OrderDetail;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 潘鑫
 * @since 2024-03-25
 */
public interface IOrderDetailService extends IService<OrderDetail> {


    List<OrderDetail> detail(String params) throws JsonProcessingException;
}
