package com.rbs.project.controller;

import com.rbs.project.exception.MyException;
import com.rbs.project.service.CClassService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * @Author: WinstonDeng
 * @Description: OOAD_Course_ManageSystem
 * @Date: Created in 10:30 2018/12/18
 * @Modified by:
 */
@RestController
@RequestMapping("/class")
public class CClassController {
    @Autowired
    CClassService cClassService;

    /**
     * Description: 上传学生名单
     * @Author: WinstonDeng
     * @Date: 11:09 2018/12/18
     */
    @PostMapping("/{classId}")
    @ResponseBody
    public ResponseEntity<String> uploadStudentFile(@PathVariable("classId") long cClassId, @RequestParam("file")MultipartFile file){
        return ResponseEntity.ok().body(cClassService.uploadStudentFile(cClassId,file));
    }

    /**
     * Description: 按id删除课程
     * @Author: WinstonDeng
     * @Date: 11:10 2018/12/18
     */
    @DeleteMapping("/{classId}")
    @ResponseBody
    public ResponseEntity<Boolean> deleteCClassById(@PathVariable("classId") long cClassId) throws MyException {
        return ResponseEntity.ok().body(cClassService.removeCClassById(cClassId));
    }

}
