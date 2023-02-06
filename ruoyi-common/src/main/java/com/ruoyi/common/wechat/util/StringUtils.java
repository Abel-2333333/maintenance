package com.ruoyi.common.wechat.util;

import cn.hutool.core.date.DateUtil;
import com.ruoyi.common.constant.HttpStatus;
import com.ruoyi.common.core.redis.RedisCache;
import com.ruoyi.common.exception.ServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author Abel
 * @since 2/6/2023 10:00 PM
 */
@Component
public class StringUtils {

    @Resource
    private  RedisCache redisCache;

    @Resource
    private  RedisUtils redisUtils;

    private final Logger logger = LoggerFactory.getLogger(getClass());

    public String getChannelCode() {
        // 生成渠道代码. 规则: yyyyMMdd + 四位递增数字(每天凌晨重置为0000), 例: 202302050001
        String yyyyMMdd = DateUtil.format(LocalDateTime.now(), "yyyyMMdd");
        String dailyChannelNum;
        try {
            Integer channelCodeVal = redisCache.getCacheObject(redisUtils.getChannelCodeKey());
            if (null == channelCodeVal) {
                channelCodeVal = 0;
            }
            AtomicInteger num = new AtomicInteger(channelCodeVal);
            dailyChannelNum = String.format("%04d", num.incrementAndGet());
            redisCache.setCacheObject(redisUtils.getChannelCodeKey(), num);
        } catch (Exception e) {
            logger.error("Redis获取当日渠道数出错");
            throw new ServiceException("添加渠道失败", HttpStatus.ERROR);
        }
        return String.format("%s%s", yyyyMMdd, dailyChannelNum);
    }
}
