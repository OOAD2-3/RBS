package com.rbs.project.controller;

import com.rbs.project.exception.MyException;
import com.rbs.project.pojo.entity.Question;
import com.rbs.project.pojo.entity.Student;
import com.rbs.project.service.AttendanceService;
import com.rbs.project.service.CClassSeminarService;
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
    private AttendanceService attendanceService;

    @Autowired
    private StudentPool studentPool;

    /**
     * Description: 写入数据库的question
     *
     * @Author: 17Wang
     * @Time: 14:18 2018/12/28
     */
    @PostMapping("/student/{studentId}")
    @ResponseBody
    public ResponseEntity<Boolean> raiseQuestion(@RequestBody Question question,@PathVariable("studentId") long studentId) throws MyException {
        Question newQuestion = new Question();

        //设置CClassSeminarId
        newQuestion.setcClassSeminarId(attendanceService.getAttendanceById(newQuestion.getAttendanceId()).getcClassSeminarId());
        //设置attendanceId
        newQuestion.setAttendanceId(question.getAttendanceId());
        //设置teamId
        newQuestion.setTeamId(question.getTeamId());
        //设置studentId
        newQuestion.setStudentId(studentId);
        //设置isSelect
        newQuestion.setSelected(Question.IS_SELECTED);
        //设置score
        newQuestion.setScore(question.getScore());

        return ResponseEntity.ok(questionService.addQuestion(newQuestion));
    }

}
