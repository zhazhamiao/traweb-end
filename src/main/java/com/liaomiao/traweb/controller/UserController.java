package com.liaomiao.traweb.controller;

import com.liaomiao.traweb.annotions.UserLoginToken;
import com.liaomiao.traweb.pojo.RespBean;
import com.liaomiao.traweb.pojo.User;
import com.liaomiao.traweb.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping("/account")
@Slf4j
public class UserController {
    UserService userService;

    //Logger logger = (Logger) LoggerFactory.getLogger(UserController.class);

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @PostMapping(value = "/register")
    public RespBean register(User user) {
        return userService.registerService(user);
    }

    @PostMapping("/login")
    public Map<String,Object> login(User user) {
        log.info("登录请求  "+"用户名: "+user.getUsername()+" 密码: "+user.getPassword());
        return userService.loginService(user);
    }
    @GetMapping("/checkRepeat")
    public RespBean checkRepeat(@RequestParam("username") String username) {
        return userService.checkRepeatService(username);
    }

    @GetMapping("/getInformation")
    @UserLoginToken
    public User getInformation(@RequestParam("username") String username) {
        return userService.getUserInformation(username);
    }

    @PutMapping("/updateInfo")
    @UserLoginToken
    public RespBean updateInfo(User user) {
        log.info("UID: "+user.getId()+" 用户信息更新请求");
        return userService.updateInformation(user);
    }

    @GetMapping("/byUid")
    public User byuid(@RequestParam("uid") Integer uid) {
        return userService.byUid(uid);
    }
}
