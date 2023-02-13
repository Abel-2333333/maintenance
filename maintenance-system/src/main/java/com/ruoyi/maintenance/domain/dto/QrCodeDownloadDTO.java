package com.ruoyi.maintenance.domain.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author Abel
 * @since 2/11/2023 3:25 PM
 */
@Data
public class QrCodeDownloadDTO {
    @NotNull(message = "id不能为空")
    private Long id;
    private int width;
    private int height;
    private boolean withLogo = false;
}
