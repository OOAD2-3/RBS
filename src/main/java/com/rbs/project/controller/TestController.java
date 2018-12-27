package com.rbs.project.controller;

import com.rbs.project.mapper.CourseMapper;
import com.rbs.project.service.AttendanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * Description:
 *
 * @Author: 17Wang
 * @Date: 17:46 2018/12/26
 */
@RestController
@RequestMapping
public class TestController {
    @Autowired
    AttendanceService attendanceService;

    @GetMapping("/test")
    @ResponseBody
    public void test(@RequestBody Map<String,Long> map){
        System.out.println(attendanceService.getMaxTeamOrderByClassIdAndSeminarId(map.get("classId"), map.get("seminarId")));
    }

}
