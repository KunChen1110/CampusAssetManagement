package com.campus.asset.query;

import lombok.Data;

import java.io.Serializable;

@Data
public class QueryFilter implements Serializable {

    private String status;
    private String location;
    private String department;
    private String category;
}
