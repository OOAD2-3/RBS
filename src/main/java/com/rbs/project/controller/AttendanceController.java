package com.rbs.project.controller;

import com.rbs.project.exception.MyException;
import com.rbs.project.pojo.entity.Attendance;
import com.rbs.project.pojo.vo.AttendanceVO;
import com.rbs.project.service.AttendanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Description:
 *
 * @Author: 17Wang
 * @Date: 11:04 2018/12/20
 */
@RestController
@RequestMapping("/attendance")
public class AttendanceController {
    @Autowired
    private AttendanceService attendanceService;

    /**
     * Description: 获取一个班级下的一节讨论课的所有的报名
     *
     * @Author: 17Wang
     * @Time: 17:57 2018/12/22
     */
    @GetMapping
    @ResponseBody
    public List<AttendanceVO> listAttendanceByCClassIdAndSeminarId(@RequestParam("cClassId") long cClassId, @RequestParam("seminarId") long seminarId) throws MyException {
        List<Attendance> attendances = attendanceService.listAttendanceByCClassIdAndSeminarId(cClassId, seminarId);
        List<AttendanceVO> attendanceVOS = new ArrayList<>();
        for (Attendance attendance : attendances) {
            attendanceVOS.add(new AttendanceVO(attendance));
        }
        return attendanceVOS;
    }

    /**
     * Description:报名一节讨论课
     *
     * @Author: 17Wang
     * @Time: 17:57 2018/12/22
     */
    @PostMapping
    @ResponseBody
    public ResponseEntity<Boolean> addAttendance(@RequestBody Map<String, Long> map) throws Exception {
        Long cClassId = map.get("classId");
        Long seminarId = map.get("seminarId");
        Long teamId = map.get("teamId");
        Integer teamOrder = Math.toIntExact(map.get("teamOrder"));
        if (cClassId == null || seminarId == null || teamId == null || teamOrder == null) {
            throw new MyException("存在为空的字段，请检查字段命名和赋值", MyException.NOT_FOUND_ERROR);
        }
        return ResponseEntity.ok(attendanceService.addAttendance(teamId, teamOrder, cClassId, seminarId));
    }

    /**
     * Description: 取消讨论课的报名
     *
     * @Author: 17Wang
     * @Time: 17:57 2018/12/22
     */
    @DeleteMapping
    @ResponseBody
    public ResponseEntity<Boolean> cancelAttendance(@RequestBody Map<String, Long> map) throws Exception {
        Long attendanceId = map.get("attendanceId");
        if (attendanceId == null) {
            throw new MyException("存在为空的字段，请检查字段命名和赋值_attendanceId", MyException.NOT_FOUND_ERROR);
        }
        return ResponseEntity.ok(attendanceService.deleteAttendance(attendanceId));
    }
}
