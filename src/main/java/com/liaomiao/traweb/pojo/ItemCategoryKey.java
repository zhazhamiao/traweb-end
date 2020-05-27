package com.liaomiao.traweb.pojo;

public class ItemCategoryKey {
    private Integer itemId;

    private Integer categoryId;

    public Integer getItemId() {
        return itemId;
    }

    public void setItemId(Integer itemId) {
        this.itemId = itemId;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public ItemCategoryKey(Integer itemId, Integer categoryId) {
        this.itemId = itemId;
        this.categoryId = categoryId;
    }
}