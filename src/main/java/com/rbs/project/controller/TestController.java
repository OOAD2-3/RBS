package com.rbs.project.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.rbs.project.dao.RoundDao;
import com.rbs.project.exception.MyException;
import com.rbs.project.mapper.StudentMapper;
import com.rbs.project.pojo.entity.Round;
import com.rbs.project.pojo.entity.Student;
import com.rbs.project.pojo.entity.Teacher;
import com.rbs.project.pojo.entity.User;
import com.rbs.project.service.EmailDemo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Description:
 *
 * @Author: 17Wang
 * @Date: 15:37 2018/12/15
 */
@RestController
public class TestController {
    @Autowired
    private StudentMapper studentMapper;

    @Autowired
    EmailDemo emailDemo;

    @GetMapping("/common/get")
    @ResponseBody
    public String get(){
        return "123";
    }

    @GetMapping("/g")
    @ResponseBody
    public String get2(){
        return "1234556789";
    }

    @RequestMapping("/test")
    public String test(){
        String s=null;
        try {
            s= emailDemo.sendEmail();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return  s;
    }

    @Autowired
    RoundDao roundDao;
    @RequestMapping("/r")
    public long round() throws MyException {
        Round round=new Round();
        round.setCourseId(1);
        round.setSerial(1);
        round.setQuestionScoreMethod(0);
        round.setReportScoreMethod(0);
        round.setPresentationScoreMethod(0);
        return  roundDao.addRound(round);
    }
}
