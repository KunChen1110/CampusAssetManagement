package com.campus.asset.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class AssetStatusUpdateDTO implements Serializable {

    private String status;
    private String remark;
}
