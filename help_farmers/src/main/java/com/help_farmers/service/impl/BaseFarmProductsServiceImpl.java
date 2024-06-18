package com.help_farmers.service.impl;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.help_farmers.common.result.ResponseCode;
import com.help_farmers.common.utils.AssertUtil;
import com.help_farmers.mapper.BaseFarmProductsMapper;
import com.help_farmers.model.domain.BaseFarmProducts;
import com.help_farmers.model.domain.Log;
import com.help_farmers.model.domain.LoginUser2;
import com.help_farmers.model.query.FarmerProductsQuery;
import com.help_farmers.model.vo.FarmProductsVo;
import com.help_farmers.model.vo.ListVo;
import com.help_farmers.service.IBaseFarmProductsService;
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
public class BaseFarmProductsServiceImpl extends ServiceImpl<BaseFarmProductsMapper, BaseFarmProducts> implements IBaseFarmProductsService {

    @Resource
    private BaseFarmProductsMapper baseFarmProductsMapper;

    @Resource
    private ILogService logService;

    @Override
    public ListVo<FarmProductsVo> getFarmProductsList(FarmerProductsQuery farmerProductsQuery) {
        IPage<FarmProductsVo> page = new Page<>(farmerProductsQuery.getPageno(), farmerProductsQuery.getPagesize());

        if (StrUtil.isBlank(farmerProductsQuery.getName())) {
            farmerProductsQuery.setName(null);
        }

        IPage<FarmProductsVo> farmerProductsVoIPage = baseFarmProductsMapper.getFarmProductsList(page, farmerProductsQuery.getNumber(), farmerProductsQuery.getName(), farmerProductsQuery.getFarmProductsTypeId());

        return new ListVo<FarmProductsVo>(farmerProductsVoIPage.getRecords(), farmerProductsVoIPage.getTotal());
    }

    @Override
    @Transactional(rollbackFor = {Exception.class})
    public void updFarm(BaseFarmProducts baseFarmProducts, LoginUser2 current) {

        // 编号，农产品名称不能为空
        AssertUtil.isTrue(ObjectUtil.isEmpty(baseFarmProducts.getNumber()), ResponseCode.NUMBER_IS_NOT_NULL);
        AssertUtil.isTrue(StrUtil.isBlank(baseFarmProducts.getName()), ResponseCode.NAME_IS_NOT_NULL);

        // 农产品编号唯一
        Integer count = baseFarmProductsMapper.selectCount(new LambdaQueryWrapper<BaseFarmProducts>().eq(BaseFarmProducts::getNumber, baseFarmProducts.getNumber()).ne(BaseFarmProducts::getName, baseFarmProducts.getName()).eq(BaseFarmProducts::getDelFlag, 0));
        AssertUtil.isTrue(!(count == 0), ResponseCode.NUMBER_IS_EXSIT);

        // 设置修改时间，修改人
        baseFarmProducts.setUpdateTime(new Date());
        baseFarmProducts.setUpdateBy(current.getUser().getUserName());

        // 更新
        AssertUtil.isTrue(!this.update(null, new UpdateWrapper<BaseFarmProducts>()
                .eq("number", baseFarmProducts.getNumber())
                        .set("number", baseFarmProducts.getNumber())
                        .set("name", baseFarmProducts.getName())
                        .set("in_price", baseFarmProducts.getInPrice())
                        .set("out_price", baseFarmProducts.getOutPrice())
                .set("unit", baseFarmProducts.getUnit())
                .set("farm_products_type_id", baseFarmProducts.getFarmProductsTypeId())
                .set("remarks", baseFarmProducts.getRemarks())
                .set("update_time", baseFarmProducts.getUpdateTime())
                .set("update_by", baseFarmProducts.getUpdateBy())
        ), ResponseCode.UPDATE_FAILED);

    }

    @Override
    @Transactional(rollbackFor = {Exception.class})
    public void saveFarm(BaseFarmProducts baseFarmProducts, LoginUser2 current) {

        // 编号，农产品名称不能为空
        AssertUtil.isTrue(ObjectUtil.isEmpty(baseFarmProducts.getNumber()), ResponseCode.NUMBER_IS_NOT_NULL);
        AssertUtil.isTrue(StrUtil.isBlank(baseFarmProducts.getName()), ResponseCode.NAME_IS_NOT_NULL);

        // 农产品编号唯一
        Integer count = baseFarmProductsMapper.selectCount(new LambdaQueryWrapper<BaseFarmProducts>().eq(BaseFarmProducts::getNumber, baseFarmProducts.getNumber()).ne(BaseFarmProducts::getName, baseFarmProducts.getName()).eq(BaseFarmProducts::getDelFlag, 0));
        AssertUtil.isTrue(!(count == 0), ResponseCode.NUMBER_IS_EXSIT);

        // 设置添加时间，添加人，逻辑删除字段
        baseFarmProducts.setCreateTime(new Date());
        baseFarmProducts.setCreateBy(current.getUser().getUserName());
        baseFarmProducts.setDelFlag("0");

        // 添加
        AssertUtil.isTrue(!this.save(baseFarmProducts), ResponseCode.SAVE_FAILED);

    }

    @Override
    @Transactional(rollbackFor = {Exception.class})
    public void delFarm(Long number) {

        // 判空
        AssertUtil.isTrue(ObjectUtil.isEmpty(number), ResponseCode.NUMBER_IS_NOT_NULL);

        // 判断删除内容是否存在
        BaseFarmProducts baseFarmProducts = baseFarmProductsMapper.selectOne(new LambdaQueryWrapper<BaseFarmProducts>().eq(BaseFarmProducts::getNumber, number).eq(BaseFarmProducts::getDelFlag, 0));
        AssertUtil.isTrue(ObjectUtil.isNull(baseFarmProducts), ResponseCode.DEL_CONTENT_ISNOTEXIST);

        UpdateWrapper<BaseFarmProducts> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("number", number)
                .set("del_flag", "1");

        AssertUtil.isTrue(!this.update(null, updateWrapper), ResponseCode.DEL_FAILED);

        LoginUser2 current = (LoginUser2) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        log.info("用户：{}，进行删除农产品操作", current.getUser().getUserName() + "---" + current.getUser().getTrueName());
        Log operateLog = new Log();
        operateLog.setName("删除农产品");
        operateLog.setUserName(current.getUser().getUserName() + "---" + current.getUser().getTrueName());
        operateLog.setCreateTime(new Date());
        operateLog.setDelFlag("0");
        AssertUtil.isTrue(!logService.save(operateLog), ResponseCode.DEL_FAILED);

    }
}
