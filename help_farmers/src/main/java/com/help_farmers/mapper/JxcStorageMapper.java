package com.help_farmers.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.help_farmers.model.domain.JxcStorage;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.help_farmers.model.vo.StorageVo;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author 潘鑫
 * @since 2024-03-24
 */
public interface JxcStorageMapper extends BaseMapper<JxcStorage> {

    IPage<StorageVo> storageList(IPage<StorageVo> page, Long farmProductsNumber, String farmProductsName);
}
