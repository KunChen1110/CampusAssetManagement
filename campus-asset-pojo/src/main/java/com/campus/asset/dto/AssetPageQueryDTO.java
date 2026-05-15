package com.campus.asset.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class AssetPageQueryDTO implements Serializable {

    private int page = 1;
    private int pageSize = 10;
    private String name;
    private Long categoryId;
    private String status;
    private String location;
    private String ownerDepartment;
}
