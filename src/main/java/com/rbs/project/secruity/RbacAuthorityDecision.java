package com.rbs.project.secruity;

import com.rbs.project.pojo.entity.Student;
import com.rbs.project.pojo.entity.Teacher;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;

import javax.servlet.http.HttpServletRequest;
import java.util.HashSet;
import java.util.Set;


/**
 * Description:
 *
 * @Author: 17Wang
 * @Date: 12:52 2018/12/10
 */
@Component("RBAC")
public class RbacAuthorityDecision {

    public boolean hasPermission(HttpServletRequest request, Authentication authentication) {

        Object userInfo = authentication.getPrincipal();

        boolean hasPermission = false;

        //获取资源
        Set<String> urls = new HashSet();
        urls.add("/user/**");
        if (userInfo instanceof Student) {
            System.out.println("我是学生");

            // 这些 url 都是要登录后才能访问，且其他的 url 都不能访问！
            // 学生的接口权限
            urls.add("/student/**");


        } else if (userInfo instanceof Teacher) {
            System.out.println("我是老师");

            // 这些 url 都是要登录后才能访问，且其他的 url 都不能访问！
            // 老师的接口权限
            urls.add("/teacher/**");
            urls.add("/student/**");
            urls.add("/course/**");
            urls.add("/class/**");
            urls.add("/team/**");
            urls.add("/attendance/**");
            urls.add("/seminarscore");
        } else {
            return false;
        }

        //当前接口和权限接口进行匹配
        AntPathMatcher antPathMatcher = new AntPathMatcher();
        for (String url : urls) {
            if (antPathMatcher.match(url, request.getRequestURI())) {
                hasPermission = true;
                break;
            }
        }

        return hasPermission;
    }
}
