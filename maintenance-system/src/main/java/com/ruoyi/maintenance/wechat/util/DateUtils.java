package com.ruoyi.maintenance.wechat.util;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

/**
 * @author Abel
 * @since 2/3/2023 11:23 PM
 */
public class DateUtils {

    /**
     * 计算当前时间距离凌晨的毫秒数
     */
    public static long getTimeFromMidnight() {
        LocalDateTime midnight = LocalDateTime.now().plusDays(1).
                withHour(0).withMinute(0).withSecond(0).withNano(0);
        return ChronoUnit.MILLIS.between(LocalDateTime.now(),midnight);
    }


}
