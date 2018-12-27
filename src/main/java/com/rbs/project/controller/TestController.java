package com.rbs.project.controller;

import com.rbs.project.dao.TeamDao;
import com.rbs.project.mapper.StudentMapper;
import com.rbs.project.service.AttendanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    @Autowired
    TeamDao teamDao;

    @GetMapping("/test")
    @ResponseBody
    public void test(){

        return;
    }


    @GetMapping("/test2")
    @ResponseBody
    public boolean test2(@RequestParam("teamId") long teamId){
        return teamDao.teamStrategy(teamId);
    }
}
