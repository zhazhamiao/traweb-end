package com.liaomiao.traweb.mapper;

import com.liaomiao.traweb.pojo.ItemCategoryExample;
import com.liaomiao.traweb.pojo.ItemCategoryKey;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ItemCategoryMapper {
    long countByExample(ItemCategoryExample example);

    int deleteByExample(ItemCategoryExample example);

    int deleteByPrimaryKey(ItemCategoryKey key);

    int insert(ItemCategoryKey record);

    int insertSelective(ItemCategoryKey record);

    List<ItemCategoryKey> selectByExample(ItemCategoryExample example);

    int updateByExampleSelective(@Param("record") ItemCategoryKey record, @Param("example") ItemCategoryExample example);

    int updateByExample(@Param("record") ItemCategoryKey record, @Param("example") ItemCategoryExample example);
}