package com.ruoyi.maintenance.domain.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.read.listener.ReadListener;
import com.alibaba.excel.util.ListUtils;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.maintenance.domain.SonyChannelCategory;
import com.ruoyi.maintenance.domain.excel.SonyChannelCategoryImportVO;
import com.ruoyi.maintenance.domain.vo.SonyChannelCategoryVO;
import com.ruoyi.maintenance.service.ISonyChannelCategoryService;
import com.ruoyi.maintenance.wechat.util.JsonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @author Abel
 * @since 2/6/2023 8:41 PM
 */
public class SonyChannelCategoryListener implements ReadListener <SonyChannelCategoryImportVO> {
    private final Logger logger = LoggerFactory.getLogger ( this.getClass () );
    private static final int BATCH_COUNT = 50;

    /**
     * 缓存的数据
     */
    private List <SonyChannelCategory> cachedUpdateDataList = ListUtils.newArrayListWithExpectedSize ( BATCH_COUNT );
    private List <SonyChannelCategory> cachedInsertDataList = ListUtils.newArrayListWithExpectedSize ( BATCH_COUNT );

    /**
     * 待注入的业务对象
     */
    private ISonyChannelCategoryService sonyChannelCategoryService;
    private final Map <String, SonyChannelCategoryVO> primaryChannelToVOMap;
    private final Map <Integer, SonyChannelCategoryVO> secondaryIdToVOMap;
    private final Map <Integer, SonyChannelCategoryVO> parentIdToVOMap;
    private final Map <String, SonyChannelCategoryVO> secondaryChannelToVOMap;



    // TODO 多线程提升效率
    private ThreadPoolTaskExecutor executor;

    /**
     * 注入业务对象到这个listener
     */
    public SonyChannelCategoryListener ( ISonyChannelCategoryService sonyChannelCategoryService, ThreadPoolTaskExecutor executor, Map <String, SonyChannelCategoryVO> primaryChannelToVOMap, Map <String, SonyChannelCategoryVO> secondaryChannelToVOMap, Map <Integer, SonyChannelCategoryVO> secondaryIdToVOMap, Map <Integer, SonyChannelCategoryVO> parentIdToVOMap ) {
        this.sonyChannelCategoryService = sonyChannelCategoryService;
        this.executor = executor;
        this.primaryChannelToVOMap = primaryChannelToVOMap;
        this.secondaryIdToVOMap = secondaryIdToVOMap;
        this.parentIdToVOMap = parentIdToVOMap;
        this.secondaryChannelToVOMap = secondaryChannelToVOMap;
    }

    /**
     * 每一条数据解析都会调用
     */
    @Override
    public void invoke ( SonyChannelCategoryImportVO data, AnalysisContext context ) {
        logger.info ( "当前数据: {}", JsonUtils.toJson ( data ) );

        Integer id = data.getId ();
        // 表格中一级渠道名
        String primaryName = data.getPrimaryName ();
        // 表格中二级渠道名
        String secondaryName = data.getSecondaryName ();
        Objects.requireNonNull ( primaryName, "一级渠道名不能为空" );
        Objects.requireNonNull ( secondaryName, "二级渠道名不能为空" );
        // 先校验一级渠道是否存在. 不存在直接报错
        if ( !primaryChannelToVOMap.containsKey ( primaryName ) ) {
            throw new ServiceException ( "一级渠道: " + primaryName + "不存在" );
        }

        // 二级渠道id是否存在
        if ( id != null ) { // 二级渠道id存在. 更新或报错
            SonyChannelCategoryVO sonyChannelCategoryVO = secondaryIdToVOMap.get ( id );
            // 判断一级渠道是否一致
            if ( parentIdToVOMap.containsKey ( sonyChannelCategoryVO.getParentId () )
                    && !parentIdToVOMap.get ( sonyChannelCategoryVO.getParentId () ).getChannelName ().equals ( primaryName ) ) { // 一级渠道名不一致
                throw new ServiceException ( "一级渠道: " + primaryName + "不匹配" );
            }

            // 二级渠道名是否一致. 一致直接跳过
            if ( !sonyChannelCategoryVO.getChannelName ().equals ( secondaryName ) ) { // 二级渠道名不一致
                // 更新二级渠道. 覆盖原来的记录
                SonyChannelCategory sonyChannelCategory = new SonyChannelCategory ();
                sonyChannelCategory.setId ( id );
                sonyChannelCategory.setParentId ( sonyChannelCategoryVO.getParentId () );
                sonyChannelCategory.setChannelName ( secondaryName );
                if ( secondaryChannelToVOMap.containsKey (secondaryName) &&
                        !secondaryChannelToVOMap.get ( secondaryName ).getId ().equals ( id ) ) {
                    throw new ServiceException ( "id: " + id + "的二级渠道名已被占用" );
                }
                sonyChannelCategory.setCreateTime ( new Date () );
                sonyChannelCategory.setCreatedBy ( SecurityUtils.getUsername () );
                cachedUpdateDataList.add ( sonyChannelCategory );
            }
        } else { // 二级渠道id不存在. 直接添加二级渠道, 因为前面校验过一级渠道是否存在
            if ( secondaryChannelToVOMap.containsKey (secondaryName)){
                throw new ServiceException ( secondaryName + " 二级渠道名已被占用" );
            }
            SonyChannelCategory sonyChannelCategory = new SonyChannelCategory ();
            sonyChannelCategory.setCreatedBy ( SecurityUtils.getUsername () );
            sonyChannelCategory.setCreateTime ( new Date () );
            sonyChannelCategory.setParentId ( primaryChannelToVOMap.get ( primaryName ).getId () );
            sonyChannelCategory.setChannelName ( secondaryName );
            cachedInsertDataList.add ( sonyChannelCategory );
        }

        // 达到BATCH_COUNT了，需要去存储一次数据库，防止数据几万条数据在内存，容易OOM
        if ( cachedUpdateDataList.size () >= BATCH_COUNT ) {
            updateData ();
            // 存储完成清理 list
            cachedUpdateDataList = ListUtils.newArrayListWithExpectedSize ( BATCH_COUNT );
        }

        if ( cachedInsertDataList.size () >= BATCH_COUNT ) {
            saveData ();
            cachedInsertDataList = ListUtils.newArrayListWithExpectedSize ( BATCH_COUNT );
        }
    }

    /**
     * 所有数据解析完成调用
     */
    @Override
    public void doAfterAllAnalysed ( AnalysisContext context ) {
        // 保存最后一批数据
        if ( cachedInsertDataList != null && !cachedInsertDataList.isEmpty () ) {
            saveData ();
        }
        if ( cachedUpdateDataList != null && !cachedUpdateDataList.isEmpty () ) {
            updateData ();
        }
        logger.info ( "所有数据解析完成" );
    }

    /**
     * 保存数据
     */
    private void saveData () {
        logger.info ( "{}条数据，开始存储数据库！", cachedInsertDataList.size () );
        sonyChannelCategoryService.batchInsert ( cachedInsertDataList );
        logger.info ( "存储数据库成功！" );
    }

    /**
     * 更新数据
     */
    private void updateData () {
        logger.info ( "{}条数据，开始更新数据库！", cachedUpdateDataList.size () );
        sonyChannelCategoryService.batchUpdate (cachedUpdateDataList);
        logger.info ( "存储数据库成功！" );
    }
}
