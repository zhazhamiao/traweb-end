package com.liaomiao.traweb.mapper;

import com.liaomiao.traweb.pojo.SecCategory;
import com.liaomiao.traweb.pojo.SecCategoryExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface SecCategoryMapper {
    long countByExample(SecCategoryExample example);

    int deleteByExample(SecCategoryExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(SecCategory record);

    int insertSelective(SecCategory record);

    List<SecCategory> selectByExample(SecCategoryExample example);

    SecCategory selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") SecCategory record, @Param("example") SecCategoryExample example);

    int updateByExample(@Param("record") SecCategory record, @Param("example") SecCategoryExample example);

    int updateByPrimaryKeySelective(SecCategory record);

    int updateByPrimaryKey(SecCategory record);
}