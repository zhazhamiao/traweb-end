package com.liaomiao.traweb.pojo;

public class UserRoleKey {
    private Integer uid;

    private Integer roleid;

    public UserRoleKey(Integer uid, Integer roleid) {
        this.uid = uid;
        this.roleid = roleid;
    }

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public Integer getRoleid() {
        return roleid;
    }

    public void setRoleid(Integer roleid) {
        this.roleid = roleid;
    }

}