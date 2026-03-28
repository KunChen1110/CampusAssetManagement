package com.sky.controller.admin;

import com.sky.dto.SetmealDTO;
import com.sky.dto.SetmealPageQueryDTO;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.SetmealService;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/setmeal")
@ApiOperation("套餐管理")
@Slf4j
public class SetmealController {

    @Autowired
    private SetmealService setmealService;

    @PostMapping
    @CacheEvict(cacheNames = "setmealCache",key = "#setmealDTO.categoryId") //删除缓存并更新
    public Result save(@RequestBody SetmealDTO setmealDTO){
        setmealService.saveWithDish(setmealDTO);
        return Result.success();
    }

    @GetMapping("/page")
    public Result<PageResult> page(SetmealPageQueryDTO setmealPageQueryDTO){
        PageResult pageResult = setmealService.pageQuery(setmealPageQueryDTO);
        return Result.success(pageResult);
    }

    @DeleteMapping
    @CacheEvict(cacheNames = "setmealCache",allEntries = true) //全部清理
    public Result delete(@RequestParam List<Long> ids){
        setmealService.deleteBatch(ids);
        return Result.success();
    }

    @PutMapping
    @CacheEvict(cacheNames = "setmealCache",allEntries = true) //全部清理
    public Result update(@RequestBody SetmealDTO setmealDTO){
        setmealService.update(setmealDTO);
        return Result.success();
    }
}
