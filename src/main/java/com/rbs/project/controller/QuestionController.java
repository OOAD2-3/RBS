package com.rbs.project.controller;

import com.rbs.project.exception.MyException;
import com.rbs.project.pojo.entity.Student;
import com.rbs.project.service.QuestionService;
import com.rbs.project.socket.StudentPool;
import com.rbs.project.utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Description:
 *
 * @Author: 17Wang
 * @Date: 15:34 2018/12/26
 */
@RestController
@RequestMapping("/question")
public class QuestionController {
    @Autowired
    private QuestionService questionService;

    @Autowired
    private StudentPool studentPool;

    /**
     * Description: 学生发起一个提问
     *
     * @Author: 17Wang
     * @Time: 14:18 2018/12/28
     */
    @PostMapping
    @ResponseBody
    public ResponseEntity<Boolean> raiseQuestion(@RequestParam("attendanceId") long attendanceId) throws MyException {
        studentPool.put(attendanceId, (Student) UserUtils.getNowUser());
        return ResponseEntity.ok(true);
    }

}
