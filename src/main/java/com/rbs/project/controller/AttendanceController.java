package com.rbs.project.controller;

import com.rbs.project.pojo.entity.Attendance;
import com.rbs.project.service.AttendanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
    public List<Attendance> listAttendanceByCClassIdAndSeminarId(@RequestParam("cClassId") long cClassId, @RequestParam("seminarId") long seminarId) {
        return attendanceService.listAttendanceByCClassIdAndSeminarId(cClassId, seminarId);
    }
}
