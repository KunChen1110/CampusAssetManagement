package com.campus.asset.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AssetStatisticsSummaryVO implements Serializable {

    private Long totalAssets;
    private Long activeAssets;
    private Long idleAssets;
    private Long maintenanceAssets;
    private BigDecimal totalOriginalValue;
}
