package com.campus.asset.dto;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class AssetUsageRecordPageQueryDTO implements Serializable {

    private int page = 1;
    private int pageSize = 10;
    private Long assetId;
    private String userName;
    private String department;
    private String status;
    private LocalDateTime beginTime;
    private LocalDateTime endTime;
}
