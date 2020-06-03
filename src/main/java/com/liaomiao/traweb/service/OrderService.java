package com.liaomiao.traweb.service;

import com.liaomiao.traweb.mapper.OrderForGoodsMapper;
import com.liaomiao.traweb.pojo.OrderForGoods;
import com.liaomiao.traweb.pojo.OrderForGoodsExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {
    OrderForGoodsMapper orderForGoodsMapper;

    @Autowired(required = false)
    public void setOrderForGoodsMapper(OrderForGoodsMapper orderForGoodsMapper) {
        this.orderForGoodsMapper = orderForGoodsMapper;
    }

    public List<OrderForGoods> getAllRelatedOrders(Integer uid,byte status) {
        OrderForGoodsExample example = new OrderForGoodsExample();
        OrderForGoodsExample.Criteria aboutSeller = example.createCriteria();
        aboutSeller.andSellerEqualTo(uid).andStatusEqualTo(status);

        OrderForGoodsExample.Criteria aboutBuyer = example.createCriteria();
        aboutBuyer.andBuyerEqualTo(uid).andStatusEqualTo(status);
        example.or(aboutBuyer);

        return orderForGoodsMapper.selectByExample(example);
    }
}
