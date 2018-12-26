package com.rbs.project.socket;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

/**
 * Description:
 *
 * @Author: 17Wang
 * @Date: 12:26 2018/12/26
 */
@Controller
public class WebSocketController {

    @MessageMapping("/hello")
    @SendTo("/topic/greetings")
    public String test(){
        return "hello this is a test";
    }
}
