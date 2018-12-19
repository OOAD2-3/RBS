package com.rbs.project.controller;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.rbs.project.exception.MyException;
import com.rbs.project.pojo.dto.CreateCClassDTO;
import com.rbs.project.pojo.entity.CClass;
import com.rbs.project.pojo.entity.Course;
import com.rbs.project.pojo.strategy.CourseMemberLimitStrategy;
import com.rbs.project.pojo.vo.CClassInfoVO;
import com.rbs.project.pojo.vo.CourseAndStrategyVO;
import com.rbs.project.pojo.vo.CourseInfoVO;
import com.rbs.project.service.CClassService;
import com.rbs.project.service.CourseService;
import com.rbs.project.utils.JsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

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
     * Description: 新建课程
     *
     * @Author: 17Wang
     * @Time: 16:46 2018/12/18
     */
    @PostMapping
    @ResponseBody
    public ResponseEntity<Boolean> createCourse(@RequestBody CourseAndStrategyVO courseAndStrategyVO) throws Exception {
        //课程基本信息
        Course temp = new Course();
        temp.setName(courseAndStrategyVO.getName());
        temp.setIntro(courseAndStrategyVO.getIntro());
        temp.setPresentationPercentage(courseAndStrategyVO.getPresentationPercentage());
        temp.setQuestionPercentage(courseAndStrategyVO.getQuestionPercentage());
        temp.setReportPercentage(courseAndStrategyVO.getReportPercentage());
        temp.setTeamStartTime(JsonUtils.StringToTimestamp(courseAndStrategyVO.getTeamStartTime()));
        temp.setTeamEndTime(JsonUtils.StringToTimestamp(courseAndStrategyVO.getTeamEndTime()));

        temp.setCourseMemberLimitStrategy(courseAndStrategyVO.getCourseMemberLimitStrategy());

        return ResponseEntity.ok(courseService.createCourse(temp));
    }

    @GetMapping
    @ResponseBody
    public List<CourseInfoVO> listMyCourses() throws MyException {
        List<CourseInfoVO> courseInfoVOS = new ArrayList<>();
        for (Course course : courseService.listMyCourses()) {
            courseInfoVOS.add(new CourseInfoVO(course));
        }
        return courseInfoVOS;
    }

    @GetMapping("/{courseId}")
    @ResponseBody
    public ResponseEntity<CourseAndStrategyVO> getCourseById(@PathVariable("courseId") long courseId) throws MyException {
        Course course = courseService.getCourseById(courseId);
        return ResponseEntity.ok(new CourseAndStrategyVO(course));
    }

    @DeleteMapping("/{courseId}")
    @ResponseBody
    public ResponseEntity<Boolean> deleteCourse(@PathVariable("courseId") long courseId) throws MyException {
        return ResponseEntity.ok(courseService.deleteCourseById(courseId));
    }


    /**
     * Description: 课程下新建班级
     *
     * @Author: WinstonDeng
     * @Date: 11:11 2018/12/12
     */
    /**
     * 初步想法先传文件，成功的话给前端一个文件名，然后创建班级时通过这个文件名去寻找文件并解析
     * 解析数据，存库建立关系
     */
    @PostMapping("/{courseId}/class")
    @ResponseBody
    public ResponseEntity<Long> createcClassInCoursePage(@PathVariable("courseId") long courseId, @RequestBody CreateCClassDTO createCClassDTO) throws MyException {
        //初始化为-1 表示新建失败
        long cclassId = -1;
        //设置班级基本信息
        CClass cClass = new CClass();
        cClass.setCourseId(cclassId);
        cClass.setGrade(createCClassDTO.getGrade());
        cClass.setSerial(createCClassDTO.getSerial());
        cClass.setTime(createCClassDTO.getTime());
        cClass.setPlace(createCClassDTO.getClassroom());
        //获得新建的课程主键
        cclassId = cClassService.createCClass(courseId, cClass);
        //解析学生名单
        if (cclassId != -1) {
            //调用解析学生名单的函数
            cClassService.transStudentListFileToDataBase(cclassId, createCClassDTO.getFileName());
        }
        return ResponseEntity.ok().body(cclassId);
    }

    /**
     * Description: 通过课程查找班级列表
     *
     * @Author: WinstonDeng
     * @Date: 10:50 2018/12/17
     */
    @GetMapping("/{courseId}/class")
    @ResponseBody
    public ResponseEntity<List<CClassInfoVO>> listAllCClassesInCoursePage(@PathVariable("courseId") long courseId) throws MyException {
        List<CClassInfoVO> cClassInfoVOS = new ArrayList<>();
        List<CClass> cClasses = cClassService.listCClassesByCourseId(courseId);
        for (CClass cClass
                : cClasses) {
            cClassInfoVOS.add(new CClassInfoVO(cClass));
        }
        return ResponseEntity.ok().body(cClassInfoVOS);
    }

}
