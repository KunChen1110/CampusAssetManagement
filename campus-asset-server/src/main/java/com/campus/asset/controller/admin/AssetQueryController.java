package com.campus.asset.controller.admin;

import com.campus.asset.query.AssetNlQueryResponse;
import com.campus.asset.query.AssetQueryService;
import com.campus.asset.dto.AssetNlQueryDTO;
import com.campus.asset.result.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/asset-query")
@Api(tags = "Controlled asset natural-language query APIs")
public class AssetQueryController {

    @Autowired
    private AssetQueryService assetQueryService;

    @PostMapping("/nl")
    @ApiOperation("Rule-based natural-language asset query")
    public Result<AssetNlQueryResponse> query(@RequestBody AssetNlQueryDTO queryDTO) {
        return Result.success(assetQueryService.query(queryDTO.getQuestion()));
    }
}
