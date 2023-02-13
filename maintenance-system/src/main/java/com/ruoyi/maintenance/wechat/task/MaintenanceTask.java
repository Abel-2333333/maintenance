package com.ruoyi.maintenance.wechat.task;

import com.ruoyi.common.core.redis.RedisCache;
import com.ruoyi.maintenance.wechat.util.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * @author Abel
 * @since 2/4/2023 12:44 AM
 */
@Slf4j
@Component("maintenanceTask")
public class MaintenanceTask {

    @Resource
    private RedisCache redisCache;

    @Resource
    private RedisUtils redisUtils;


    /**
     * 重置每日新增的渠道个数
     */
    public void resetDailyChannelNum() {
        String channelCodeKey = redisUtils.getChannelCodeKey();
        redisCache.setCacheObject(channelCodeKey, 0, 24, TimeUnit.HOURS);
        log.info("{} 重置 每日新增渠道数", new Date());
    }

}
