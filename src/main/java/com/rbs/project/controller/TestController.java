package com.rbs.project.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.rbs.project.mapper.StudentMapper;
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
}
