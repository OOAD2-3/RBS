package com.rbs.project.socket;

import com.rbs.project.pojo.entity.Attendance;
import com.rbs.project.pojo.entity.Question;
import com.rbs.project.pojo.vo.AttendanceVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

/**
 * Description:
 *
 * @Author: 17Wang
 * @Date: 12:26 2018/12/26
 */
@Controller
public class WebSocketController {

    @Autowired
    private QuestionPool questionPool;

    @MessageMapping("/hello")
    @SendTo("/topic/greetings")
    public Map<String, String> test(Long message) {
        Map<String, String> map = new HashMap<>();
        System.out.println(message);
        //map.put("hello", message.get("name"));
        return map;
    }

    /**
     * Description:
     * 老师发送一个到服务器说切换到下一组
     * 把当前的Attandance信息发送给所有订阅了SendTo的学生
     *
     * @Author: 17Wang
     * @Time: 14:28 2018/12/26
     */
    @MessageMapping("/teacher/class/{classId}/seminar/{seminarId}/nextTeam")
    @SendTo("/student/class/{classId}/seminar/{seminarId}/nextTeam")
    public AttendanceVO nextTeam(Map<String, String> message) {
        return null;
    }
}
