package com.rbs.project.socket;

import com.rbs.project.dao.AttendanceDao;
import com.rbs.project.exception.MyException;
import com.rbs.project.pojo.entity.Attendance;
import com.rbs.project.pojo.entity.Student;
import com.rbs.project.pojo.entity.User;
import com.rbs.project.controller.vo.AttendanceVO;
import com.rbs.project.controller.vo.UserVO;
import com.rbs.project.secruity.jwt.JwtUserDetailsService;
import com.rbs.project.service.AttendanceService;
import com.rbs.project.service.StudentService;
import com.rbs.project.utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.HashMap;
import java.util.Map;

/**
 * Description:
 *
 * @Author: 17Wang
 * @Date: 12:26 2018/12/26
 */
@Controller
@CrossOrigin
public class WebSocketController {

    @Autowired
    private StudentPool studentPool;

    @Autowired
    private StudentService studentService;

    @MessageMapping("/student/{studentId}")
    @SendTo("/topic/client/student/{studentId}")
    public Integer questionStudentNumber(@DestinationVariable("studentId") long studentId, Map<String, Long> msg) {
        Long attendanceId = msg.get("attendanceId");
        return studentPool.size(attendanceId);
    }

    /**
     * Description:
     * 老师发送一个到服务器说切换到下一组
     * 把当前的Attandance信息发送给所有订阅了SendTo的客户端（教师，学生）
     *
     * @Author: 17Wang
     * @Time: 14:28 2018/12/26
     */
    @MessageMapping("/teacher/class/{classId}/seminar/{seminarId}/nextTeam")
    @SendTo("/topic/client/class/{classId}/seminar/{seminarId}/nextTeam")
    public Integer nextTeam(@DestinationVariable("classId") long classId,
                            @DestinationVariable("seminarId") long seminarId,
                            Integer teamOrder) throws Exception {
        return teamOrder;
    }

    /**
     * Description: 老师点击抽取提问按钮，获得一个学生
     * 把当前的学生信息发送给所有订阅了SendTo的客户端（教师，学生）
     *
     * @Author: 17Wang
     * @Time: 15:31 2018/12/26
     */
    @MessageMapping("/teacher/class/{classId}/seminar/{seminarId}/pickQuestion")
    @SendTo("/topic/client/class/{classId}/seminar/{seminarId}/pickQuestion")
    public UserVO pickQuestion(@DestinationVariable("classId") long classId, @DestinationVariable("seminarId") long seminarId, Long attendanceId) {
        System.out.println("pickQuestion:" + attendanceId);
        Student student = studentPool.pick(attendanceId);
        if (student == null) {
            return null;
        }
        return new UserVO(studentPool.pick(attendanceId));
    }

    /**
     * Description: 学生点击提问
     * 1、将提问的学生放到提问池中
     * 2、给订阅了SendTo的客户端发送 当前提问的数量
     *
     * @Author: 17Wang
     * @Time: 2:30 2018/12/29
     */
    @MessageMapping("/teacher/class/{classId}/seminar/{seminarId}/raiseQuestion")
    @SendTo("/topic/client/class/{classId}/seminar/{seminarId}/raiseQuestion")
    public Integer raiseQuestion(@DestinationVariable("classId") long classId, @DestinationVariable("seminarId") long seminarId, Map<String, Long> msg) throws Exception {
        Long attendanceId = msg.get("attendanceId");
        Long studentId = msg.get("studentId");
        System.out.println("msg: " + attendanceId + " " + studentId);

        studentPool.put(attendanceId, studentService.findStudentById(studentId));
        return studentPool.size(attendanceId);
    }

}
