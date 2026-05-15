package com.campus.asset.mapper;

import com.github.pagehelper.Page;
import com.campus.asset.dto.AssetPageQueryDTO;
import com.campus.asset.entity.Asset;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface AssetMapper {

    @Insert("insert into asset(asset_code, name, category_id, location, owner_department, status, purchase_date, original_value, create_time, update_time) " +
            "values(#{assetCode}, #{name}, #{categoryId}, #{location}, #{ownerDepartment}, #{status}, #{purchaseDate}, #{originalValue}, #{createTime}, #{updateTime})")
    @org.apache.ibatis.annotations.Options(useGeneratedKeys = true, keyProperty = "id")
    void insert(Asset asset);

    void update(Asset asset);

    @Delete("delete from asset where id = #{id}")
    void deleteById(Long id);

    @Select("select * from asset where id = #{id}")
    Asset getById(Long id);

    Page<Asset> pageQuery(AssetPageQueryDTO queryDTO);
}
