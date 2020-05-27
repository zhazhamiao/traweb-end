package com.liaomiao.traweb.aspect;

import com.liaomiao.traweb.mapper.UserRoleMapper;
import com.liaomiao.traweb.pojo.UserRoleExample;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Aspect
@Component
@Slf4j
public class DefaultRoleAspect {
    UserRoleMapper roleMapper;

    @Autowired(required = false)
    public void setRoleMapper(UserRoleMapper roleMapper) {
        this.roleMapper = roleMapper;
    }

    @Pointcut("execution (public * com.liaomiao.traweb.service.defaultrole.*.*(..))")
    public void pointcut() {
    }

    @Around("pointcut()")
    public void checkRole(ProceedingJoinPoint pjd) {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        assert attributes != null;
        HttpServletRequest request = attributes.getRequest();
        HttpServletResponse response = attributes.getResponse();
        String uid = request.getParameter("uid");

        try {
            UserRoleExample roleExample = new UserRoleExample();
            UserRoleExample.Criteria criteria = roleExample.createCriteria();
            // 该用户普通用户权限是否启用中(roleId=1)
            criteria.andUidEqualTo(Integer.parseInt(uid));
            criteria.andRoleidEqualTo(1);
            criteria.andEnableEqualTo((byte) 1);

            if (roleMapper.selectByExample(roleExample).size() == 0) {
                assert response != null;
                response.sendError(403, "操作被拒绝,用户权限不足");
                throw new RuntimeException("用户权限不足");
            } else {
                log.info("uid:" + uid + " 执行默认用户权限操作");
                pjd.proceed();
            }
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
    }

}
