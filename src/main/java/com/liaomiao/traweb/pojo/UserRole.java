package com.liaomiao.traweb.pojo;

import java.util.Date;

public class UserRole extends UserRoleKey {
    private Date addtime;

    private Byte enable;

    public UserRole(Integer uid, Integer roleid, Date addtime, Byte enable) {
        super(uid, roleid);
        this.addtime = addtime;
        this.enable = enable;
    }

    public Date getAddtime() {
        return addtime;
    }

    public void setAddtime(Date addtime) {
        this.addtime = addtime;
    }

    public Byte getEnable() {
        return enable;
    }

    public void setEnable(Byte enable) {
        this.enable = enable;
    }

}