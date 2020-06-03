package com.liaomiao.traweb.controller;

import com.liaomiao.traweb.annotions.UserLoginToken;
import com.liaomiao.traweb.pojo.Item;
import com.liaomiao.traweb.pojo.RespBean;
import com.liaomiao.traweb.service.defaultrole.DefaultRoleItemService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
@RequestMapping("/vocation")
@CrossOrigin
@Slf4j
public class VocationController {

    DefaultRoleItemService defaultRoleItemService;

    @Autowired
    public void setDefaultRoleItemService(DefaultRoleItemService defaultRoleItemService) {
        this.defaultRoleItemService = defaultRoleItemService;
    }

    @RequestMapping(value = "/getMessage")
    //@UserLoginToken
    public String getMessage() {
        return "123";
    }

    @PostMapping(value = "/addItem")
    @UserLoginToken
    public RespBean addItem(@RequestParam("itemImg") MultipartFile[] upload, Item item,
                            @RequestParam("categoryId") int categoryId) {
        return defaultRoleItemService.addItemService(upload[0], item, categoryId);
    }

    @PostMapping(value = "/uploadOrder")
    @UserLoginToken
    public void uploadOrder(@RequestParam("buyer") Integer buyer, @RequestParam("itemId") Integer itemId) {
        defaultRoleItemService.createOrder(buyer,itemId);
    }

    @PutMapping(value = "/cancelOrder")
    @UserLoginToken
    public RespBean cancelOrder(@RequestParam("orderId") Integer orderId, HttpServletResponse response) throws IOException {
        log.info("取消订单编号 "+orderId+" 请求");
        return defaultRoleItemService.cancelOrder(orderId,response);
    }

    @PutMapping(value = "/completeOrder")
    public RespBean completeOrder(@RequestParam("orderId") Integer orderId) {
        log.info("订单编号: "+orderId+ " 完成");
        return defaultRoleItemService.completeOrder(orderId);
    }
}
