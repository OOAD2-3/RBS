package com.rbs.project.socket;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.rbs.project.pojo.RespInfo;
import com.rbs.project.pojo.entity.Student;
import com.rbs.project.pojo.entity.Teacher;
import com.rbs.project.pojo.entity.User;
import com.rbs.project.secruity.jwt.JwtUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * Description:
 *
 * @Author: 17Wang
 * @Date: 10:18 2018/12/25
 */
@ServerEndpoint(value = "/websocket/{semianrClassId}/{account}")
@Component
public class WebSocket {

    private static int onlineCount = 0;

    private static CopyOnWriteArraySet<WebSocket> webSockets = new CopyOnWriteArraySet<>();

    private Session session;

    private Long seminarClassId;
    private User user;

    //当做是一种依赖注入吧
    private static ApplicationContext applicationContext;

    public static void setApplicationContext(ApplicationContext context) {
        applicationContext = context;
    }

    @OnOpen
    public void onOpen(Session session, @PathParam("semianrClassId") Long semianrClassId, @PathParam("account") String account) {
        this.session = session;
        this.seminarClassId = semianrClassId;
        this.user = applicationContext.getBean(JwtUserDetailsService.class).loadUserByUsername(account);
        System.out.println("seminarClassId:" + seminarClassId);
        System.out.println(user);

        webSockets.add(this);
        onlineCount++;
        System.out.println("有新连接加入！当前在线人数为" + onlineCount);
        sendMessage(JSON.toJSONString(true));
    }

    @OnClose
    public void onClose() {
        webSockets.remove(this);
        onlineCount--;
        System.out.println("有一连接关闭！当前在线人数为" + onlineCount);
    }

    /**
     * 老师点击下一个队伍时 返回下一个队伍
     * 提问
     * 抽取提问
     */
    @OnMessage
    public void onMessage(String message, Session session) {
        System.out.println("来自客户端的消息：" + message);

        try {
            JSONObject jsonObject= (JSONObject) JSON.parse(message);
            System.out.println(jsonObject);
        }catch (Exception e){

        }


        //群发消息
        for (WebSocket webSocket : webSockets) {
            if (webSocket.user instanceof Student && webSocket.seminarClassId.equals(this.seminarClassId)) {
                webSocket.sendMessage(message);
            }else{
                webSocket.sendMessage(JSON.toJSONString(webSocket.user));
            }
        }
    }

    /**
     * 实现服务器主动推送
     */
    public void sendMessage(String message) {
        try {
            this.session.getBasicRemote().sendText(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
