package com.campus.asset.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class AssetCategoryDTO implements Serializable {

    private Long id;
    private String name;
    private Integer type;
    private String description;
    private Integer status;
}
