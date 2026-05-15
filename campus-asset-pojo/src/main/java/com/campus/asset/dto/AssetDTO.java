package com.campus.asset.dto;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class AssetDTO implements Serializable {

    private Long id;
    private String assetCode;
    private String name;
    private Long categoryId;
    private String location;
    private String ownerDepartment;
    private String status;
    private LocalDate purchaseDate;
    private BigDecimal originalValue;
}
