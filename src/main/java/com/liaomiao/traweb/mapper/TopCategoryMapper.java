package com.liaomiao.traweb.mapper;

import com.liaomiao.traweb.pojo.TopCategory;
import com.liaomiao.traweb.pojo.TopCategoryExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TopCategoryMapper {
    long countByExample(TopCategoryExample example);

    int deleteByExample(TopCategoryExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(TopCategory record);

    int insertSelective(TopCategory record);

    List<TopCategory> selectByExample(TopCategoryExample example);

    TopCategory selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") TopCategory record, @Param("example") TopCategoryExample example);

    int updateByExample(@Param("record") TopCategory record, @Param("example") TopCategoryExample example);

    int updateByPrimaryKeySelective(TopCategory record);

    int updateByPrimaryKey(TopCategory record);
}