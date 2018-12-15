package com.rbs.project.secruity;

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
@Component("rbacauthorityservice")
public class RbacAuthorityDecision {
    public boolean hasPermission(HttpServletRequest request, Authentication authentication) {

        Object userInfo = authentication.getPrincipal();

        boolean hasPermission = false;

        System.out.println(request.getRequestURI());
        if (request.getMethod() == "OPTIONS") {
            return true;
        }

        if (userInfo instanceof UserDetails) {

            String username = ((UserDetails) userInfo).getUsername();

            //获取资源
            Set<String> urls = new HashSet();

            urls.add("/common/**"); // 这些 url 都是要登录后才能访问，且其他的 url 都不能访问！

            AntPathMatcher antPathMatcher = new AntPathMatcher();

            for (String url : urls) {
                if (antPathMatcher.match(url, request.getRequestURI())) {
                    hasPermission = true;
                    break;
                }
            }

            return hasPermission;
        } else {
            return false;
        }
    }
}
