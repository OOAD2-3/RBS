package com.rbs.project.controller;

import com.rbs.project.exception.MyException;
import com.rbs.project.pojo.entity.Teacher;
import com.rbs.project.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Description:teacher资源接口
 *
 * @Author: 17Wang
 * @Date: 11:53 2018/12/16
 */
@RestController
@RequestMapping("/teacher")
public class TeacherController {
    @Autowired
    private UserService userService;

    @PutMapping("/active")
    @ResponseBody
    public ResponseEntity<Boolean> teacherActive(@RequestBody Teacher teacher) throws MyException {
        return ResponseEntity.ok(userService.userActivation(teacher));
    }
}
