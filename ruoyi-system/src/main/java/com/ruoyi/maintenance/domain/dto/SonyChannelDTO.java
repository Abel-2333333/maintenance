package com.ruoyi.maintenance.domain.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

@Data
@Accessors(chain = true)
public class SonyChannelDTO implements Serializable {

    private static final long serialVersionUID = 6693709373498809000L;

    private String primaryChannel;

    private String secondaryChannel;

    private String maintenanceStationName;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date startTime;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date endTime;

}
