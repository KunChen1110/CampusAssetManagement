package com.campus.asset.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class AssetCategoryDistributionVO implements Serializable {

    private Long categoryId;
    private String categoryName;
    private Long assetCount;
}
