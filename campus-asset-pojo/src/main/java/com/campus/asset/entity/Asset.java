package com.campus.asset.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Asset implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    private String assetCode;
    private String name;
    private Long categoryId;
    private String location;
    private String ownerDepartment;
    private String status;
    private LocalDate purchaseDate;
    private BigDecimal originalValue;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
