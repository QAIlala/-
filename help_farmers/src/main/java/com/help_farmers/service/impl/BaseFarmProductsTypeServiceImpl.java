package com.help_farmers.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.help_farmers.common.result.ResponseCode;
import com.help_farmers.common.utils.AssertUtil;
import com.help_farmers.mapper.BaseFarmProductsTypeMapper;
import com.help_farmers.model.domain.BaseFarmProductsType;
import com.help_farmers.model.domain.Log;
import com.help_farmers.model.domain.LoginUser2;
import com.help_farmers.model.query.BaseQuery;
import com.help_farmers.model.vo.ListVo;
import com.help_farmers.service.IBaseFarmProductsTypeService;
import com.help_farmers.service.ILogService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 潘鑫
 * @since 2024-03-22
 */
@Service
@Slf4j
public class BaseFarmProductsTypeServiceImpl extends ServiceImpl<BaseFarmProductsTypeMapper, BaseFarmProductsType> implements IBaseFarmProductsTypeService {

    @Resource
    private BaseFarmProductsTypeMapper baseFarmProductsTypeMapper;

    @Resource
    private ILogService logService;

    @Override
    public ListVo<BaseFarmProductsType> typeList(BaseQuery baseQuery) {

        IPage<BaseFarmProductsType> page = new Page<>(baseQuery.getPageno(), baseQuery.getPagesize());

        IPage<BaseFarmProductsType> baseFarmProductsTypeIPage = baseFarmProductsTypeMapper.selectPage(page, new LambdaQueryWrapper<BaseFarmProductsType>().eq(BaseFarmProductsType::getDelFlag, 0));

        return new ListVo<BaseFarmProductsType>(baseFarmProductsTypeIPage.getRecords(), baseFarmProductsTypeIPage.getTotal());
    }

    @Override
    @Transactional(rollbackFor = {Exception.class})
    public void updType(BaseFarmProductsType baseFarmProductsType, LoginUser2 current) {
        AssertUtil.isTrue(StrUtil.isBlank(baseFarmProductsType.getName()), ResponseCode.NAME_IS_NOT_NULL);
        baseFarmProductsType.setUpdateTime(new Date());
        baseFarmProductsType.setUpdateBy(current.getUser().getUserName());
        AssertUtil.isTrue(!this.updateById(baseFarmProductsType), ResponseCode.UPDATE_FAILED);
    }

    @Override
    @Transactional(rollbackFor = {Exception.class})
    public void delType(Integer typeId) {
        /**
         * Id 非空
         * 根据Id查询的对象非空
         * del_flag = 1
         */
        AssertUtil.isTrue(null == typeId, ResponseCode.PLEASE_SELECT_DEL_CONTENT);
        BaseFarmProductsType baseFarmProductsType = baseFarmProductsTypeMapper.selectById(typeId);
        AssertUtil.isTrue(null == baseFarmProductsType, ResponseCode.DEL_CONTENT_ISNOTEXIST);
        baseFarmProductsType.setDelFlag("1");
        AssertUtil.isTrue(!this.updateById(baseFarmProductsType), ResponseCode.DEL_FAILED);

        LoginUser2 current = (LoginUser2) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        log.info("用户：{}，进行删除农产品类型操作", current.getUser().getUserName() + "---" + current.getUser().getTrueName());
        Log operateLog = new Log();
        operateLog.setName("删除农产品类型");
        operateLog.setUserName(current.getUser().getUserName() + "---" + current.getUser().getTrueName());
        operateLog.setCreateTime(new Date());
        operateLog.setDelFlag("0");
        AssertUtil.isTrue(!logService.save(operateLog), ResponseCode.DEL_FAILED);

    }

    @Override
    @Transactional(rollbackFor = {Exception.class})
    public void saveType(BaseFarmProductsType baseFarmProductsType, LoginUser2 current) {

        /**
         * 农产品类别名称非空 唯一
         */
        String typeName = baseFarmProductsType.getName();
        AssertUtil.isTrue(StrUtil.isBlank(typeName), ResponseCode.NAME_IS_NOT_NULL);
        AssertUtil.isTrue(null != baseFarmProductsTypeMapper.selectOne(new LambdaQueryWrapper<BaseFarmProductsType>().eq(BaseFarmProductsType::getName, typeName).eq(BaseFarmProductsType::getDelFlag, 0)), ResponseCode.NAME_IS_EXSIT);
        baseFarmProductsType.setCreateTime(new Date());
        baseFarmProductsType.setCreateBy(current.getUser().getUserName());
        baseFarmProductsType.setDelFlag("0");
        AssertUtil.isTrue(!this.save(baseFarmProductsType), ResponseCode.SAVE_FAILED);
    }


}
