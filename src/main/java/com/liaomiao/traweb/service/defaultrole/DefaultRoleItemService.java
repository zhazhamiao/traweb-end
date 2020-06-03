package com.liaomiao.traweb.service.defaultrole;

import com.liaomiao.traweb.mapper.ItemCategoryMapper;
import com.liaomiao.traweb.mapper.ItemMapper;
import com.liaomiao.traweb.mapper.OrderForGoodsMapper;
import com.liaomiao.traweb.pojo.Item;
import com.liaomiao.traweb.pojo.ItemCategoryKey;
import com.liaomiao.traweb.pojo.OrderForGoods;
import com.liaomiao.traweb.pojo.RespBean;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.weaver.ast.Or;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;

import javax.naming.OperationNotSupportedException;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.UUID;

/*
* 所有需要默认权限的service
* */
@Service
@Slf4j
public class DefaultRoleItemService {

    ItemMapper itemMapper;
    ItemCategoryMapper itemCategoryMapper;
    OrderForGoodsMapper orderMapper;

    @Autowired(required = false)
    public void setItemMapper(ItemMapper itemMapper) {
        this.itemMapper = itemMapper;
    }
    @Autowired(required = false)
    public void setItemCategoryMapper(ItemCategoryMapper itemCategoryMapper) {
        this.itemCategoryMapper = itemCategoryMapper;
    }
    @Autowired(required = false)
    public void setOrderMapper(OrderForGoodsMapper orderMapper) {
        this.orderMapper = orderMapper;
    }

    @Transactional
    public RespBean addItemService(MultipartFile file, Item item, int categoryId) {
        System.out.println(item);
        String path = "/upload/static/images/item_image";
        String filename = file.getOriginalFilename();
        //截取出文件后缀
        assert filename != null;
        String suffix = filename.substring(filename.lastIndexOf(".") + 1);
        //文件名为唯一值[UUID+商家uid]
        String uuid = UUID.randomUUID().toString().replace("-", "").substring(0, 16);
        filename = uuid + "_" + "uid" + item.getSeller() + '.' + suffix;
        try {
            file.transferTo(new File(path, filename));
            item.setImg("/static/images/item_image/"+filename);
            //0表示待审核
            item.setStatus((byte)0);
            itemMapper.insertSelective(item);
            System.out.println(item.getId());
            itemCategoryMapper.insert(new ItemCategoryKey(item.getId(),categoryId));
        } catch (Exception e) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            e.printStackTrace();
            return new RespBean("failed", "商品添加失败");
        }
        return new RespBean("success","商品添加成功");
    }

    @Transactional
    public void createOrder(Integer buyer,Integer itemId) {
        log.info("提交订单请求");
        // 首先item-1,如果为0 设置status为已完成(2)
        Item item = itemMapper.selectByPrimaryKey(itemId);
        item.setQuantity(item.getQuantity()-1);
        if (item.getQuantity() < 0) {
            throw new RuntimeException("商品已售空");
        }
        if (item.getQuantity() == 0) {
            item.setStatus((byte)2);
        }
        // status 0进行中 1已完成 2已取消
        OrderForGoods order = new OrderForGoods((byte)0,itemId,item.getSeller(),buyer);
        order.setLaunchDate(new Date());
        itemMapper.updateByPrimaryKeySelective(item);
        orderMapper.insertSelective(order);
    }
    /*
    * 取消一个订单
    * */
    @Transactional
    public RespBean cancelOrder(Integer orderId, HttpServletResponse response) throws IOException {
        OrderForGoods order = orderMapper.selectByPrimaryKey(orderId);
        // 如该订单已取消或已完成
        if (order.getStatus() == 2|| order.getStatus() == 1) {
            response.sendError(403,"不支持的操作");
            throw new RuntimeException("不支持的操作");
        }else {
            order.setStatus((byte)2);
            order.setEndDate(new Date());
            Item item = itemMapper.selectByPrimaryKey(order.getItemId());
            item.setQuantity(item.getQuantity()+1);
            item.setStatus((byte)1);
            itemMapper.updateByPrimaryKeySelective(item);
            orderMapper.updateByPrimaryKeySelective(order);
        }
        return new RespBean("success","订单已取消");
    }

    /*
    * 完成一个订单
    * */
    public RespBean completeOrder(Integer orderId) {
        OrderForGoods order = new OrderForGoods();
        order.setOrderId(orderId);
        order.setStatus((byte) 1);
        order.setEndDate(new Date());
        orderMapper.updateByPrimaryKeySelective(order);
        return new RespBean("success","订单已完成");
    }
}
