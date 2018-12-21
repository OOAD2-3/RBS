package com.rbs.project.controller;

import com.rbs.project.exception.MyException;
import com.rbs.project.pojo.entity.Attendance;
import com.rbs.project.pojo.vo.AttendanceVO;
import com.rbs.project.service.AttendanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

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

    @GetMapping
    @ResponseBody
    public List<AttendanceVO> listAttendanceByCClassIdAndSeminarId(@RequestParam("cClassId") long cClassId, @RequestParam("seminarId") long seminarId) throws MyException {
        List<Attendance> attendances = attendanceService.listAttendanceByCClassIdAndSeminarId(cClassId, seminarId);
        List<AttendanceVO> attendanceVOS=new ArrayList<>();
        for (Attendance attendance:attendances){
            attendanceVOS.add(new AttendanceVO(attendance));
        }
        return attendanceVOS;
    }
}
