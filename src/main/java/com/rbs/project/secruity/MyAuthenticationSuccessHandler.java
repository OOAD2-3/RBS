package com.rbs.project.secruity;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rbs.project.dao.UserDao;
import com.rbs.project.pojo.RespInfo;
import com.rbs.project.secruity.jwt.JwtUserDetailsService;
import com.rbs.project.utils.JwtTokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;


/**
 * Description:
 *
 * @Author: 17Wang
 * @Date: 12:24 2018/12/10
 */
@Component
public class MyAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
    @Autowired
    private JwtUserDetailsService jwtUserDetailsService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {

        String username=(String) authentication.getPrincipal();

        RespInfo respInfo=new RespInfo();
        respInfo.setStatus(200);
        respInfo.setMsg("Login Success!");
        respInfo.setObj(jwtUserDetailsService.loadUserByUsername(username).isActive());

        //生成token
        String jwtToken = JwtTokenUtils.generateToken(username, 7*24*60*60);
        respInfo.setJwtToken(jwtToken);

        //httpServletResponse.setHeader("active",jwtUserDetailsService.loadUserByUsername(username).getActive(); );
        httpServletResponse.setStatus(respInfo.getStatus());
        httpServletResponse.setContentType("application/json;charset=utf-8");
        ObjectMapper om = new ObjectMapper();
        PrintWriter out = httpServletResponse.getWriter();
        out.write(om.writeValueAsString(respInfo));
        out.flush();
        out.close();
    }
}
