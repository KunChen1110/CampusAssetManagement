package com.campus.asset.mapper;

import com.github.pagehelper.Page;
import com.campus.asset.dto.AssetCategoryPageQueryDTO;
import com.campus.asset.entity.AssetCategory;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface AssetCategoryMapper {

    @Insert("insert into asset_category(name, type, description, status, create_time, update_time) " +
            "values(#{name}, #{type}, #{description}, #{status}, #{createTime}, #{updateTime})")
    void insert(AssetCategory assetCategory);

    void update(AssetCategory assetCategory);

    @Delete("delete from asset_category where id = #{id}")
    void deleteById(Long id);

    Page<AssetCategory> pageQuery(AssetCategoryPageQueryDTO queryDTO);

    List<AssetCategory> list(AssetCategoryPageQueryDTO queryDTO);

    @Select("select count(*) from asset where category_id = #{categoryId}")
    Integer countAssetsByCategoryId(Long categoryId);
}
