package com.campus.asset.dto;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class AssetUsageRecordDTO implements Serializable {

    private Long id;
    private Long assetId;
    private String userName;
    private String department;
    private LocalDateTime usageStartTime;
    private LocalDateTime usageEndTime;
    private String purpose;
    private String status;
}
