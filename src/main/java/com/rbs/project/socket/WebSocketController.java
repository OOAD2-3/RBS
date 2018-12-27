package com.rbs.project.socket;

import com.rbs.project.dao.AttendanceDao;
import com.rbs.project.pojo.entity.Attendance;
import com.rbs.project.pojo.entity.Student;
import com.rbs.project.pojo.entity.User;
import com.rbs.project.pojo.vo.AttendanceVO;
import com.rbs.project.pojo.vo.UserVO;
import com.rbs.project.service.AttendanceService;
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
    private AttendanceService attendanceService;

    @MessageMapping("/hello")
    @SendTo("/topic/greetings")
    public Map<String,String> test(Map<String,String> message){
        Map<String,String> map=new HashMap<>();
        return message;
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
    public AttendanceVO nextTeam(@DestinationVariable("classId") long classId,
                                 @DestinationVariable("seminarId") long seminarId,
                                 Long attendanceId) throws Exception {
        Attendance attendance = attendanceService.getAttendanceById(attendanceId);
        //测试成功没问题 最大TeamOrder
        long maxTeamOrder = attendanceService.getMaxTeamOrderByClassIdAndSeminarId(classId,seminarId);

        //修改当前讨论课的状态
        attendanceService.turnStatusToIsPresent(attendanceId);
        //找出下一个即将进行的信息
        Attendance nextAtteandance = null;
        int teamOrder = attendance.getTeamOrder() + 1;
        while (attendance == null && teamOrder <= maxTeamOrder) {
            attendance.setTeamOrder(teamOrder);
            nextAtteandance = attendanceService.getAttendanceBycClassIdAndSeminarIdAndTeamOrder(attendance, AttendanceDao.HAS_TEAM);
            teamOrder++;
        }
        //清空已展示完的提问信息
        studentPool.clearAll(attendanceId);

        //如果是最后一组展示完了，返回null
        if (attendance == null) {
            return null;
        }

        //返回下一组展示的信息
        return new AttendanceVO(nextAtteandance);
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
        return new UserVO(studentPool.pick(attendanceId));
    }
}
