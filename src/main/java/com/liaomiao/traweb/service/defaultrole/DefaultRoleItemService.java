package com.liaomiao.traweb.service.defaultrole;

import com.liaomiao.traweb.mapper.ItemCategoryMapper;
import com.liaomiao.traweb.mapper.ItemMapper;
import com.liaomiao.traweb.pojo.Item;
import com.liaomiao.traweb.pojo.ItemCategoryKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.UUID;

/*
* 所有需要默认权限的service
* */
@Service
public class DefaultRoleItemService {

    ItemMapper itemMapper;
    ItemCategoryMapper itemCategoryMapper;
    @Autowired(required = false)
    public void setItemMapper(ItemMapper itemMapper) {
        this.itemMapper = itemMapper;
    }
    @Autowired(required = false)
    public void setItemCategoryMapper(ItemCategoryMapper itemCategoryMapper) {
        this.itemCategoryMapper = itemCategoryMapper;
    }

    @Transactional
    public void addItemService(MultipartFile file, Item item, int categoryId) {
        System.out.println(item);
        String path = "F:\\static\\images\\item_image";
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
        }
    }
}
