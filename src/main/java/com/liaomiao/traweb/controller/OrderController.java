package com.liaomiao.traweb.controller;

import com.liaomiao.traweb.pojo.OrderForGoods;
import com.liaomiao.traweb.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/order")
@CrossOrigin
public class OrderController {

    OrderService orderService;

    @Autowired
    public void setOrderService(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/getRelatedOrders")
    public List<OrderForGoods> getRelatedOrders(@RequestParam("uid") Integer uid,
                                                @RequestParam("status")byte status) {
        return orderService.getAllRelatedOrders(uid,status);
    }
}
