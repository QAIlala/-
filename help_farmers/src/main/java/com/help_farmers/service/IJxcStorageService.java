package com.help_farmers.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.help_farmers.model.domain.JxcStorage;
import com.help_farmers.model.domain.LoginUser2;
import com.help_farmers.model.query.StorageAndOutPutQuery;
import com.help_farmers.model.vo.ListVo;
import com.help_farmers.model.vo.StorageVo;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 潘鑫
 * @since 2024-03-24
 */
public interface IJxcStorageService extends IService<JxcStorage> {

    ListVo<StorageVo> storageList(StorageAndOutPutQuery storageQuery);

    JxcStorage selectOne(Long farmProductsNumber, Integer wId);

    void saveStorage(JxcStorage jxcStorage, LoginUser2 current);

    void delById(Integer[] sids);
}
