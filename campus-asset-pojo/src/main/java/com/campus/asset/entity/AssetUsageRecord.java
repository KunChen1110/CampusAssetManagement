package com.campus.asset.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AssetUsageRecord implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    private Long assetId;
    private String userName;
    private String department;
    private LocalDateTime usageStartTime;
    private LocalDateTime usageEndTime;
    private String purpose;
    private String status;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
