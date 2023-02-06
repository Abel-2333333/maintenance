package com.ruoyi.maintenance.domain.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.read.listener.ReadListener;
import com.alibaba.excel.util.ListUtils;
import com.ruoyi.common.wechat.util.JsonUtils;
import com.ruoyi.maintenance.domain.excel.SonyChannelImportVO;
import com.ruoyi.maintenance.service.ISonyChannelService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.List;

/**
 * @author Abel
 * @since 2/6/2023 8:41 PM
 */
public class SonyChannelListener implements ReadListener<SonyChannelImportVO> {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private static final int BATCH_COUNT = 50;

    /**
     * 缓存的数据
     */
    private List<SonyChannelImportVO> cachedUpdateDataList = ListUtils.newArrayListWithExpectedSize(BATCH_COUNT);
    private List<SonyChannelImportVO> cachedInsertDataList = ListUtils.newArrayListWithExpectedSize(BATCH_COUNT);

    /**
     * 待注入的业务对象
     */
    private ISonyChannelService sonyChannelService;

    // TODO 多线程提升效率
    private ThreadPoolTaskExecutor executor;

    /**
     * 注入业务对象到这个listener
     */
    public SonyChannelListener(ISonyChannelService sonyChannelService, ThreadPoolTaskExecutor executor) {
        this.sonyChannelService = sonyChannelService;
        this.executor = executor;
    }

    /**
     * 每一条数据解析都会调用
     */
    @Override
    public void invoke(SonyChannelImportVO data, AnalysisContext context) {
        logger.info("当前数据: {}", JsonUtils.toJson(data));
        if (data.getId() != null) {
            cachedUpdateDataList.add(data);
        } else {
//            WechatUtil.getQRCodeUrl()
            cachedInsertDataList.add(data);
        }

        // 达到BATCH_COUNT了，需要去存储一次数据库，防止数据几万条数据在内存，容易OOM
        if (cachedUpdateDataList.size() >= BATCH_COUNT) {
            updateData();
            // 存储完成清理 list
            cachedUpdateDataList = ListUtils.newArrayListWithExpectedSize(BATCH_COUNT);
        }

        if (cachedInsertDataList.size() >= BATCH_COUNT) {
            saveData();
            cachedInsertDataList = ListUtils.newArrayListWithExpectedSize(BATCH_COUNT);
        }
    }

    /**
     * 所有数据解析完成调用
     */
    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {
        // 保存最后一批数据
        if (cachedInsertDataList != null && !cachedInsertDataList.isEmpty()) {
            saveData();
        }
        if (cachedUpdateDataList != null && !cachedUpdateDataList.isEmpty()) {
            updateData();
        }
        logger.info("所有数据解析完成");
    }

    /**
     * 保存数据
     */
    private void saveData() {
        logger.info("{}条数据，开始存储数据库！", cachedInsertDataList.size());
        sonyChannelService.batchAddChannel(cachedInsertDataList);
        logger.info("存储数据库成功！");
    }

    /**
     * 更新数据
     */
    private void updateData() {
        logger.info("{}条数据，开始更新数据库！", cachedUpdateDataList.size());
        sonyChannelService.batchUpdateSonyChannel(cachedUpdateDataList);
        logger.info("存储数据库成功！");
    }
}
