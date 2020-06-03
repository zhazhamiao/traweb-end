package com.liaomiao.traweb.mapper;

import com.liaomiao.traweb.pojo.OrderForGoods;
import com.liaomiao.traweb.pojo.OrderForGoodsExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface OrderForGoodsMapper {
    long countByExample(OrderForGoodsExample example);

    int deleteByExample(OrderForGoodsExample example);

    int deleteByPrimaryKey(Integer orderId);

    int insert(OrderForGoods record);

    int insertSelective(OrderForGoods record);

    List<OrderForGoods> selectByExample(OrderForGoodsExample example);

    OrderForGoods selectByPrimaryKey(Integer orderId);

    int updateByExampleSelective(@Param("record") OrderForGoods record, @Param("example") OrderForGoodsExample example);

    int updateByExample(@Param("record") OrderForGoods record, @Param("example") OrderForGoodsExample example);

    int updateByPrimaryKeySelective(OrderForGoods record);

    int updateByPrimaryKey(OrderForGoods record);
}