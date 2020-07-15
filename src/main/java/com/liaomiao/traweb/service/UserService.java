package com.liaomiao.traweb.service;

import com.liaomiao.traweb.mapper.UserMapper;
import com.liaomiao.traweb.mapper.UserRoleMapper;
import com.liaomiao.traweb.pojo.RespBean;
import com.liaomiao.traweb.pojo.User;
import com.liaomiao.traweb.pojo.UserExample;
import com.liaomiao.traweb.pojo.UserRole;
import com.liaomiao.traweb.utils.TokenUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class UserService {

    UserMapper userMapper;
    UserRoleMapper userRoleMapper;

    @Autowired(required = false)
    public void setUserMapper(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @Autowired(required = false)
    public void setUserRoleMapper(UserRoleMapper userRoleMapper) {
        this.userRoleMapper = userRoleMapper;
    }

    /*
     * 用户注册
     * 注册成功后为该用户添加一个普通用户权限
     * */
    @Transactional
    public RespBean registerService(User userRecord) {
        try {
            //格式化时间
            Date date = new Date();
//            String strDateFormat = "yyyy-MM-dd HH:mm:ss";
//            SimpleDateFormat sdf = new SimpleDateFormat(strDateFormat);
//            Date formatDate = sdf.parse(sdf.format(date));
            userRecord.setRegdate(date);

            userRecord.setSex((byte) 2);
            userRecord.setName("");
            //默认的头像
            userRecord.setImg("/static/images/user_image/default.jpg");
            userRecord.setPhone("");
            //插入后返回自增主键
            userMapper.insert(userRecord);
            Integer uid = userRecord.getId();
            log.info("UID: "+uid+" 注册成功");
            //为用户赋予普通用户权限
            UserRole userRole = new UserRole(uid, 1, date, (byte) 1);
            userRoleMapper.insert(userRole);
            log.info("INSERT ROLE ID: 1 "+"UID: "+uid);
        } catch (Exception e) {
            e.printStackTrace();
            //手动回滚
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return new RespBean("failed", "注册失败了");
        }
        return new RespBean("success", "注册成功");
    }

    //登录校验
    public Map<String,Object> loginService(User user) {
        Map<String,Object> resultMap = new HashMap<>();
        UserExample userExample = new UserExample();
        UserExample.Criteria criteria = userExample.createCriteria();
        criteria.andUsernameEqualTo(user.getUsername());
        criteria.andPasswordEqualTo(user.getPassword());

        List<User> users = userMapper.selectByExample(userExample);
        if (users.isEmpty()) {
            resultMap.put("rpb", new RespBean("failed", "用户名或密码错误"));
            return resultMap;
        }

/*        User currentUser = users.get(0);
        //登录成功后将用户信息放入session中
        HttpSession httpSession = servletRequest.getSession();
        //如果session中有信息 先清空
        Enumeration<?> enumeration = httpSession.getAttributeNames();
        while (enumeration.hasMoreElements()) {
            String name = enumeration.nextElement().toString();
            httpSession.removeAttribute(name);
        }*/

        /*httpSession.setAttribute("user", currentUser);*/

        /*//然后查询该用户所包含的权限，也放入session中
        UserRoleExample roleExample = new UserRoleExample();
        UserRoleExample.Criteria roleExampleCriteria = roleExample.createCriteria();
        roleExampleCriteria.andUidEqualTo(currentUser.getId());
        List<UserRole> userRoles = userRoleMapper.selectByExample(roleExample);
        for (UserRole role : userRoles) {
            //如果这个权限启用中
            if (role.getEnable().equals((byte)1)) {
                httpSession.setAttribute(
                        String.valueOf(role.getRoleid()),currentUser.getUsername());
            }
        }*/

        String token = TokenUtil.getToken(users.get(0));

        log.info("用户登录成功，生成token： "+ token);
        //resultMap.put("userInfo", users.get(0));
        resultMap.put("rpb", new RespBean("success", "登录成功"));
        resultMap.put("token",token);
        resultMap.put("uid",users.get(0).getId());
        return resultMap;
    }

    public RespBean checkRepeatService(String username) {
        UserExample userExample = new UserExample();
        UserExample.Criteria criteria = userExample.createCriteria();
        criteria.andUsernameEqualTo(username);

        List<User> users = userMapper.selectByExample(userExample);
        if (!users.isEmpty()) {
            return new RespBean("failed", "该用户名重复");
        }
        return new RespBean("success", "可以使用的用户名");
    }

    public User getUserInformation(String username) {
        return userMapper.selectByUserName(username);
    }

    /*
    * 更新用户信息
    * */
    public RespBean updateInformation(User user) {
       if(userMapper.updateByPrimaryKeySelective(user) == 1) {
            return new RespBean("success","用户信息更新成功");
       }else
           return new RespBean("failed","用户信息更新失败");

    }

    public User byUid(Integer uid) {
        User user = userMapper.selectByPrimaryKey(uid);
        // 部分无用但敏感的信息不希望暴露出去
        user.setPhone("");
        user.setName("");
        user.setAddrId(null);
        return user;
    }

    public User byUidDetailed(Integer uid) {
        return userMapper.selectByPrimaryKey(uid);
    }
}
