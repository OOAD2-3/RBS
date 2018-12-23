package com.rbs.project.controller;

import com.rbs.project.exception.MyException;
import com.rbs.project.pojo.dto.CreateCClassDTO;
import com.rbs.project.pojo.entity.*;
import com.rbs.project.pojo.strategy.CourseMemberLimitStrategy;
import com.rbs.project.pojo.vo.*;
import com.rbs.project.service.*;
import com.rbs.project.utils.FileLoadUtils;
import com.rbs.project.utils.JsonUtils;
import com.rbs.project.utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


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
    private CClassService cClassService;

    @Autowired
    private RoundService roundService;

    @Autowired
    private TeamService teamService;

    @Autowired
    private StudentService studentService;

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
        Course course = new Course();
        course.setName(courseAndStrategyVO.getName());
        course.setIntro(courseAndStrategyVO.getIntro());
        course.setPresentationPercentage(courseAndStrategyVO.getPresentationPercentage());
        course.setQuestionPercentage(courseAndStrategyVO.getQuestionPercentage());
        course.setReportPercentage(courseAndStrategyVO.getReportPercentage());
        course.setTeamStartTime(JsonUtils.StringToTimestamp(courseAndStrategyVO.getTeamStartTime()));
        course.setTeamEndTime(JsonUtils.StringToTimestamp(courseAndStrategyVO.getTeamEndTime()));

        course.setCourseMemberLimitStrategy(courseAndStrategyVO.getCourseMemberLimitStrategy());
        course.setConflictCourses(courseAndStrategyVO.getConflictCourses());
        //如果人数策略
        if (course.getCourseMemberLimitStrategy() == null) {
            CourseMemberLimitStrategy courseMemberLimitStrategy = new CourseMemberLimitStrategy();
            courseMemberLimitStrategy.setMaxMember(99);
            courseMemberLimitStrategy.setMinMember(0);
            course.setCourseMemberLimitStrategy(courseMemberLimitStrategy);
        }
        //冲突策略为空时
        if (course.getConflictCourses() == null) {
            course.setConflictCourses(new ArrayList<>());
        }
        //设置最大人数
        if (course.getCourseMemberLimitStrategy().getMaxMember() == null) {
            course.getCourseMemberLimitStrategy().setMaxMember(99);
        }
        //设置最小人数
        if (course.getCourseMemberLimitStrategy().getMinMember() == null) {
            course.getCourseMemberLimitStrategy().setMinMember(0);
        }

        if (course.getName() == null) {
            throw new MyException("课程名不能为空", MyException.ERROR);
        }
        if (course.getTeamStartTime() == null) {
            throw new MyException("组队开始时间不能为空", MyException.ERROR);
        }
        if (course.getTeamEndTime() == null) {
            throw new MyException("组队结束时间不能为空", MyException.ERROR);
        }
        if (course.getPresentationPercentage() == null ||
                course.getQuestionPercentage() == null ||
                course.getReportPercentage() == null) {
            throw new MyException("计算分数规则不能为空", MyException.ERROR);
        }
        return ResponseEntity.ok(courseService.createCourse(course));
    }

    /**
     * Description: 获取所有课程
     * @Author: 17Wang
     * @Time: 17:38 2018/12/23
    */
    @GetMapping("/all")
    @ResponseBody
    public List<CourseInfoVO> listAllCourses(){
        List<CourseInfoVO> courseInfoVOS=new ArrayList<>();
        for(Course course:courseService.listAllCourses()){
            courseInfoVOS.add(new CourseInfoVO(course));
        }
        return courseInfoVOS;
    }

    /**
     * Description: 获取我的所有课程
     *
     * @Author: 17Wang
     * @Time: 21:29 2018/12/22
     */
    @GetMapping
    @ResponseBody
    public List<CourseInfoVO> listMyCourses() throws MyException {
        List<CourseInfoVO> courseInfoVOS = new ArrayList<>();
        for (Course course : courseService.listMyCourses()) {
            courseInfoVOS.add(new CourseInfoVO(course));
        }
        return courseInfoVOS;
    }

    /**
     * Description: 通过courseId获取一个课程
     *
     * @Author: 17Wang
     * @Time: 21:30 2018/12/22
     */
    @GetMapping("/{courseId}")
    @ResponseBody
    public ResponseEntity<CourseAndStrategyVO> getCourseById(@PathVariable("courseId") long courseId) throws MyException {
        Course course = courseService.getCourseById(courseId);
        return ResponseEntity.ok(new CourseAndStrategyVO(course));
    }

    /**
     * Description: 删除一个课程
     *
     * @Author: 17Wang
     * @Time: 21:40 2018/12/22
     */
    @DeleteMapping("/{courseId}")
    @ResponseBody
    public ResponseEntity<Boolean> deleteCourse(@PathVariable("courseId") long courseId) throws Exception {
        return ResponseEntity.ok(courseService.deleteCourseById(courseId));
    }

    /**
     * Description: 获取课程下的所有小组
     *
     * @Author: 17Wang
     * @Time: 11:20 2018/12/23
     */
    @GetMapping("/{courseId}/team")
    @ResponseBody
    public List<TeamBaseInfoVO> listTeamAtCourse(@PathVariable("courseId") long courseId) throws MyException {
        List<Team> teams = teamService.listTeamByCourseId(courseId);
        List<TeamBaseInfoVO> teamBaseInfoVOS = new ArrayList<>();
        for (Team team : teams) {
            teamBaseInfoVOS.add(new TeamBaseInfoVO(team));
        }
        return teamBaseInfoVOS;
    }

    /**
     * Description:
     *
     * @Author: 17Wang
     * @Time: 11:44 2018/12/23
     */
    @GetMapping("/{courseId}/team/mine")
    @ResponseBody
    public Map<String, Object> getMyTeam(@PathVariable("courseId") long courseId) throws MyException {
        Student nowStudent = (Student) UserUtils.getNowUser();
        Team team = teamService.getTeamByCourseIdAndStudentId(courseId, nowStudent.getId());
        Map<String, Object> map = new HashMap<>();
        map.put("id", team.getId());
        map.put("name", team.getName());
        map.put("course", new CourseInfoVO(team.getCourse()));
        map.put("class", new CClassInfoVO(team.getcClass()));
        map.put("leader", new UserVO(team.getLeader()));

        List<UserVO> userVOS = new ArrayList<>();
        for (Student student : team.getStudents()) {
            if (student.getId() != team.getLeader().getId()) {
                userVOS.add(new UserVO(student));
            }
        }

        map.put("members", userVOS);
        return map;
    }

    /**
     * Description: 查找一个课程下未组队的学生
     *
     * @Author: 17Wang
     * @Time: 12:04 2018/12/23
     */
    @GetMapping("/{courseId}/team/free")
    @ResponseBody
    public List<UserVO> listFreeStudentAtCourse(@PathVariable("courseId") long courseId) {
        List<Student> students = studentService.listByCourseIdAndTeamId(courseId);
        List<UserVO> userVOS = new ArrayList<>();
        for (Student student : students) {
            userVOS.add(new UserVO(student));
        }
        return userVOS;
    }

    /**
     * Description: 创建班级时，上传学生名单
     *
     * @Author: WinstonDeng
     * @Date: 17:18 2018/12/19
     */
    @PostMapping("/{courseId}/class/studentfile")
    @ResponseBody
    public ResponseEntity<String> uploadStudentFile(@RequestParam("file") MultipartFile file) {
        return ResponseEntity.ok().body(FileLoadUtils.upload(file));
    }

    /**
     * Description: 创建班级，如果有学生名单（DTO里fileName非空），则解析存库
     *
     * @Author: WinstonDeng
     * @Date: 11:11 2018/12/12
     */
    @PostMapping("/{courseId}/class")
    @ResponseBody
    public ResponseEntity<Long> createcClassInCoursePage(@PathVariable("courseId") long courseId, @RequestBody CreateCClassDTO createCClassDTO) throws MyException {
        //初始化为-1 表示新建失败
        long cclassId = -1;
        //设置班级基本信息
        CClass cClass = new CClass();
        cClass.setCourseId(courseId);
        cClass.setGrade(createCClassDTO.getGrade());
        cClass.setSerial(createCClassDTO.getSerial());
        cClass.setTime(createCClassDTO.getTime());
        cClass.setPlace(createCClassDTO.getClassroom());
        //获得新建的课程主键
        cclassId = cClassService.createCClass(courseId, cClass);
        //解析学生名单
        if (cclassId != -1) {
            //调用解析学生名单的函数

            if (createCClassDTO.getFileName() != null) {
                cClassService.transStudentListFileToDataBase(cclassId, createCClassDTO.getFileName());
            }
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

    /**
     * Description: 在课程下查看讨论课
     *
     * @Author: WinstonDeng
     * @Date: 17:08 2018/12/21
     */
    @GetMapping("/{courseId}/seminars")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> listAllSeminarsUnderRoundInCoursePage(@PathVariable("courseId") long courseId) throws MyException {
        if ((Long) courseId == null) {
            throw new MyException("课程id不能为空", MyException.ERROR);
        }
        Map<String, Object> map = new HashMap<>();
        map.put("courseId", courseId);
        map.put("courseName", courseService.getCourseById(courseId).getName());
        List<CClass> cClasses = cClassService.listCClassesByCourseId(courseId);
        List<CClassInfoVO> cClassInfoVOS = new ArrayList<>();
        for (CClass cClass
                : cClasses) {
            cClassInfoVOS.add(new CClassInfoVO(cClass));
        }
        map.put("cClasses", cClassInfoVOS);
        List<Round> rounds = roundService.listRoundsByCourseId(courseId);
        List<RoundInfoVO> roundInfoVOS = new ArrayList<>();
        for (Round round
                : rounds) {
            roundInfoVOS.add(new RoundInfoVO(round));
        }
        map.put("rounds", roundInfoVOS);
        return ResponseEntity.ok().body(map);
    }
}
