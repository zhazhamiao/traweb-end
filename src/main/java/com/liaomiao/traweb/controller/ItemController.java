package com.liaomiao.traweb.controller;

import com.liaomiao.traweb.pojo.Item;
import com.liaomiao.traweb.pojo.SecCategory;
import com.liaomiao.traweb.pojo.TopCategory;
import com.liaomiao.traweb.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/item")
@CrossOrigin
public class ItemController {
    ItemService itemService;

    @Autowired
    public void setItemService(ItemService itemService) {
        this.itemService = itemService;
    }

    /*
     * 获取所有的一级分类
     * */
    @GetMapping("getTopCategories")
    public List<TopCategory> getTopCategories() {
        return itemService.getTopCategories();
    }

    /*
     * 根据一级分类id获得下属的二级分类
     * */
    @GetMapping("getSecCategories")
    public List<SecCategory> getSecCategories(@RequestParam("topCategoryId") int topCategoryId) {
        return itemService.getSecCategories(topCategoryId);
    }

    @GetMapping("getItemsByCategory")
    public List<Item> getItemsByCategory(@RequestParam("categoryId") int categoryId, @RequestParam("page") int page) {
        return itemService.getItemsByCategoryId(page, categoryId);
    }

    @GetMapping("itemsStatus")
    public List<Item> itemsStatus(@RequestParam("uid") int uid,
                                  @RequestParam("status") int status) {
        return itemService.itemsStatus(uid,status);
    }
    @GetMapping("getItemById")
    public Item getItemById(@RequestParam("itemId")Integer id) {
        return itemService.getItemById(id);
    }
}
