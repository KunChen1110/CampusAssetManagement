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
public class AssetOperationLog implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    private Long assetId;
    private String operationType;
    private String oldStatus;
    private String newStatus;
    private String remark;
    private Long operatorId;
    private LocalDateTime createTime;
}
