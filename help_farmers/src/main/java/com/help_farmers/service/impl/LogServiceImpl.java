package com.help_farmers.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.help_farmers.common.result.ResponseCode;
import com.help_farmers.common.utils.AssertUtil;
import com.help_farmers.mapper.LogMapper;
import com.help_farmers.model.domain.Log;
import com.help_farmers.model.domain.LoginUser2;
import com.help_farmers.service.ILogService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 潘鑫
 * @since 2024-03-25
 */
@Service
@Slf4j
public class LogServiceImpl extends ServiceImpl<LogMapper, Log> implements ILogService {

    @Resource
    private LogMapper logMapper;

    @Override
    @Transactional(rollbackFor = {Exception.class})
    public void delById(Integer[] logIds, LoginUser2 current) {
        /**
         * 入库记录id集合非空 长度不为0
         * 根据id查询出要删除的数据并保存在集合中
         * 批处理删除
         */

        AssertUtil.isTrue(null == logIds || logIds.length == 0, ResponseCode.PLEASE_SELECT_DEL_CONTENT);

        List<Log> logs = new ArrayList<>();
        for (Integer id : logIds) {
            Log temp = this.getById(id);
            temp.setDelFlag("1");
            logs.add(temp);
        }
        AssertUtil.isTrue(!this.updateBatchById(logs), ResponseCode.DEL_FAILED);

        log.info("用户：{}，进行删除日志操作", current.getUser().getUserName() + "---" + current.getUser().getTrueName());
        Log operateLog = new Log();
        operateLog.setName("删除日志");
        operateLog.setUserName(current.getUser().getUserName() + "---" + current.getUser().getTrueName());
        operateLog.setCreateTime(new Date());
        operateLog.setDelFlag("0");
        AssertUtil.isTrue(!this.save(operateLog), ResponseCode.DEL_FAILED);
    }
}
