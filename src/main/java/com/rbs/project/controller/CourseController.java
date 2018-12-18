package com.rbs.project.controller;

import com.rbs.project.exception.MyException;
import com.rbs.project.pojo.dto.CreateCClassDTO;
import com.rbs.project.pojo.entity.CClass;
import com.rbs.project.pojo.vo.CClassInfoVO;
import com.rbs.project.service.CClassService;
import com.rbs.project.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    private CourseService courseService;

    @Autowired
    CClassService cClassService;




    /**
     * Description: 课程下新建班级
     *
     * @Author: WinstonDeng
     * @Date: 11:11 2018/12/12
     */
    /**
     *  初步想法先传文件，成功的话给前端一个文件名，然后创建班级时通过这个文件名去寻找文件并解析
     *  解析数据，存库建立关系
     */
    @PostMapping("/{courseId}/class")
    @ResponseBody
    public ResponseEntity<Long> createcClassInCoursePage(@PathVariable("courseId") long courseId, @RequestBody CreateCClassDTO createCClassDTO) throws MyException{
        //初始化为-1 表示新建失败
        long cclassId= -1;
        //设置班级基本信息
        CClass cClass=new CClass();
        cClass.setCourseId(cclassId);
        cClass.setGrade(createCClassDTO.getGrade());
        cClass.setSerial(createCClassDTO.getSerial());
        cClass.setTime(createCClassDTO.getTime());
        cClass.setPlace(createCClassDTO.getClassroom());
        //获得新建的课程主键
        cclassId=cClassService.createCClass(courseId,cClass);
        //解析学生名单
        if(cclassId!=-1){
            //调用解析学生名单的函数
            cClassService.transStudentListFileToDataBase(cclassId,createCClassDTO.getFileName());
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
