package com.help_farmers.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.help_farmers.model.domain.JxcOutput;
import com.help_farmers.model.domain.LoginUser2;
import com.help_farmers.model.query.StorageAndOutPutQuery;
import com.help_farmers.model.vo.ListVo;
import com.help_farmers.model.vo.OutputVo;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 潘鑫
 * @since 2024-03-24
 */
public interface IJxcOutputService extends IService<JxcOutput> {

    ListVo<OutputVo> outputList(StorageAndOutPutQuery outputQuery);

    void delById(Integer[] oids);

    void saveOutput(JxcOutput jxcOutput, LoginUser2 current);
}
