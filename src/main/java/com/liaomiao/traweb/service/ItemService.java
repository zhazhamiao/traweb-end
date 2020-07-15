package com.liaomiao.traweb.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.liaomiao.traweb.mapper.ItemCategoryMapper;
import com.liaomiao.traweb.mapper.ItemMapper;
import com.liaomiao.traweb.mapper.SecCategoryMapper;
import com.liaomiao.traweb.mapper.TopCategoryMapper;
import com.liaomiao.traweb.pojo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ItemService {

    TopCategoryMapper topCategoryMapper;
    SecCategoryMapper secCategoryMapper;
    ItemCategoryMapper itemCategoryMapper;
    ItemMapper itemMapper;

    @Autowired(required = false)
    public void setTopCategoryMapper(TopCategoryMapper topCategoryMapper) {
        this.topCategoryMapper = topCategoryMapper;
    }
    @Autowired(required = false)
    public void setSecCategoryMapper(SecCategoryMapper secCategoryMapper) {
        this.secCategoryMapper = secCategoryMapper;
    }
    @Autowired(required = false)
    public void setItemCategoryMapper(ItemCategoryMapper itemCategoryMapper) {
        this.itemCategoryMapper = itemCategoryMapper;
    }
    @Autowired(required = false)
    public void setItemMapper(ItemMapper itemMapper) {
        this.itemMapper = itemMapper;
    }

    // 获取所有一级分类
    public List<TopCategory> getTopCategories() {
        return topCategoryMapper.selectByExample(new TopCategoryExample());
    }

    // 通过一级分类获取对应的二级分类
    public List<SecCategory> getSecCategories(int topCategoryId) {
        SecCategoryExample example = new SecCategoryExample();
        SecCategoryExample.Criteria criteria = example.createCriteria();
        criteria.andCidEqualTo(topCategoryId);
        return secCategoryMapper.selectByExample(example);
    }

    /*
    * 通过类别搜索商品
    * */
    public List<Item> getItemsByCategoryId(int index ,int categoryId) {
        // 使用pageHelper对查询到的数据进行分类
        Page<Object> page = PageHelper.startPage(index,10);
        PageInfo<Item> info = new PageInfo<>(itemMapper.selectItemsByCategoryId(categoryId));
        return info.getList();
    }

    public List<Item> itemsStatus(int uid,int status) {
        ItemExample example = new ItemExample();
        ItemExample.Criteria criteria = example.createCriteria();
        criteria.andSellerEqualTo(uid);
        criteria.andStatusEqualTo((byte)status);
        example.setOrderByClause("item.id desc");

        return itemMapper.selectByExample(example);
    }

    public Item getItemById(int id) {
        return itemMapper.selectByPrimaryKey(id);
    }

    public List<Item> onSearchService(String keyword,Integer index) {
        ItemExample example = new ItemExample();
        ItemExample.Criteria criteria = example.createCriteria();
        Page<Object> page = PageHelper.startPage(index,10);

        criteria.andNameLike("%"+keyword+"%");
        criteria.andStatusEqualTo((byte)1);
        example.setOrderByClause("item.id desc");
        PageInfo<Item> info = new PageInfo<>(itemMapper.selectByExample(example));
        return info.getList();
    }
}
