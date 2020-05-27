package com.liaomiao.traweb.controller;

import com.liaomiao.traweb.annotions.UserLoginToken;
import com.liaomiao.traweb.pojo.Item;
import com.liaomiao.traweb.service.defaultrole.DefaultRoleItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/vocation")
@CrossOrigin
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
    public void addItem(@RequestParam("itemImg") MultipartFile[] upload, Item item,
                            @RequestParam("categoryId") int categoryId) {
        defaultRoleItemService.addItemService(upload[0], item, categoryId);
    }
}
