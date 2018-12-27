package com.rbs.project.controller;

import com.rbs.project.mapper.StudentMapper;
import com.rbs.project.pojo.entity.Student;
import com.rbs.project.mapper.CourseMapper;
import com.rbs.project.service.AttendanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
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

    @Autowired
    StudentMapper studentMapper;

    @GetMapping("/test")
    @ResponseBody
    public void test(@RequestBody Map<String,Long> map){
        System.out.println(attendanceService.getMaxTeamOrderByClassIdAndSeminarId(map.get("classId"), map.get("seminarId")));
    }


    @GetMapping("/test2")
    @ResponseBody
    public List<Student> test2(@RequestParam("courseId") long courseId, @RequestParam("teamId") long teamId){
        return studentMapper.findByCourseIdAndTeamId(courseId, teamId);
    }
}
