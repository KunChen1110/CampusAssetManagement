package com.campus.asset.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class AssetUsageRankingVO implements Serializable {

    private Long assetId;
    private String assetCode;
    private String assetName;
    private Long usageCount;
}
