package com.rbs.project.controller;

import com.rbs.project.exception.MyException;
import com.rbs.project.pojo.entity.Student;
import com.rbs.project.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Description:student资源接口
 *
 * @Author: 17Wang
 * @Date: 11:53 2018/12/16
 */
@RestController
@RequestMapping("/student")
public class StudentController {
    @Autowired
    private UserService userService;

    @PutMapping("/active")
    @ResponseBody
    public ResponseEntity<Boolean> studentActive(@RequestBody Student student) throws MyException {
        return ResponseEntity.ok(userService.userActivation(student));
    }
}
