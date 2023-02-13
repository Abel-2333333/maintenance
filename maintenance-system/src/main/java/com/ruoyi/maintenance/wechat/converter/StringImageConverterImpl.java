package com.ruoyi.maintenance.wechat.converter;

import com.alibaba.excel.converters.WriteConverterContext;
import com.alibaba.excel.converters.string.StringImageConverter;
import com.alibaba.excel.metadata.data.WriteCellData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Abel
 * @since 2/6/2023 1:08 AM
 */
public class StringImageConverterImpl extends StringImageConverter {
    private final Logger logger = LoggerFactory.getLogger(getClass());
    @Override
    public WriteCellData<?> convertToExcelData(WriteConverterContext<String> context) {

        try {
            return super.convertToExcelData(context);
        } catch (Exception e) {
            logger.error("转换{}字段出错", context.getValue(),e);
            throw new RuntimeException(e);
        }
    }
}
