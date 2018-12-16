package com.rbs.project.controller;

import com.rbs.project.exception.MyException;
import com.rbs.project.pojo.entity.Student;
import com.rbs.project.pojo.entity.User;
import com.rbs.project.pojo.vo.UserVO;
import com.rbs.project.service.StudentService;
import com.rbs.project.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

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

    @Autowired
    private StudentService studentService;

    @ResponseBody
    public List<UserVO> listAllStudents(){
        List<Student> students=studentService.listAllStudents();
        List<UserVO> userVOS=new ArrayList<>();
        for (Student student:students){
            userVOS.add(new UserVO(student));
        }
        return userVOS;
    }

    @PutMapping("/active")
    @ResponseBody
    public ResponseEntity<Boolean> studentActive(@RequestBody Student student) throws MyException {
        return ResponseEntity.ok(userService.userActivation(student));
    }
}
