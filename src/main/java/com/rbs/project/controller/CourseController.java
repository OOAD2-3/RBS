package com.rbs.project.controller;

import com.rbs.project.exception.MyException;
import com.rbs.project.pojo.dto.CreateCClassDTO;
import com.rbs.project.pojo.entity.CClass;
import com.rbs.project.pojo.vo.CClassInfoVO;
import com.rbs.project.service.CClassService;
import com.rbs.project.utils.FileLoadUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: WinstonDeng
 * @Description: OOAD_Course_ManageSystem
 * @Date: Created in 14:38 2018/12/16
 * @Modified by:
 */
@RestController
@RequestMapping("/course")
public class CourseController {
    @Autowired
    CClassService cClassService;

    /**
     * Description: 创建班级时，上传学生名单
     * @Author: WinstonDeng
     * @Date: 17:18 2018/12/19
     */
    @PostMapping("/{courseId}/class/studentfile")
    @ResponseBody
    public ResponseEntity<String> uploadStudentFile(@RequestParam("file") MultipartFile file){
        return ResponseEntity.ok().body(FileLoadUtils.upload(file));
    }
    /**
     * Description: 创建班级，如果有学生名单（DTO里fileName非空），则解析存库
     * @Author: WinstonDeng
     * @Date: 11:11 2018/12/12
     */
    @PostMapping("/{courseId}/class")
    @ResponseBody
    public ResponseEntity<Long> createcClassInCoursePage(@PathVariable("courseId") long courseId,@RequestBody CreateCClassDTO createCClassDTO) throws MyException{
        //初始化为-1 表示新建失败
        long cclassId= -1;
        //设置班级基本信息
        CClass cClass=new CClass();
        cClass.setCourseId(courseId);
        cClass.setGrade(createCClassDTO.getGrade());
        cClass.setSerial(createCClassDTO.getSerial());
        cClass.setTime(createCClassDTO.getTime());
        cClass.setPlace(createCClassDTO.getClassroom());
        //获得新建的课程主键
        cclassId=cClassService.createCClass(courseId,cClass);
        //解析学生名单
        if(cclassId!=-1){
            //调用解析学生名单的函数
            if(createCClassDTO.getFileName()!=null){
                cClassService.transStudentListFileToDataBase(cclassId,createCClassDTO.getFileName());
            }
        }
        return ResponseEntity.ok().body(cclassId);
    }

    /**
     * Description: 通过课程查找班级列表
     * @Author: WinstonDeng
     * @Date: 10:50 2018/12/17
     */
    @GetMapping("/{courseId}/class")
    @ResponseBody
    public ResponseEntity<List<CClassInfoVO>> listAllCClassesInCoursePage(@PathVariable("courseId") long courseId)throws MyException{
        List<CClassInfoVO> cClassInfoVOS=new ArrayList<>();
        List<CClass> cClasses=cClassService.listCClassesByCourseId(courseId);
        for(CClass cClass
                :cClasses){
            cClassInfoVOS.add(new CClassInfoVO(cClass));
        }
        return ResponseEntity.ok().body(cClassInfoVOS);
    }

}
