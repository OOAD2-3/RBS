package com.rbs.project.dao;

import com.rbs.project.exception.MyException;
import com.rbs.project.mapper.*;
import com.rbs.project.mapper.strategy.ConflictCourseStrategyMapper;
import com.rbs.project.mapper.strategy.CourseMemberLimitStrategyMapper;
import com.rbs.project.pojo.entity.Course;
import com.rbs.project.pojo.strategy.CourseMemberLimitStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * Description:
 *
 * @Author: 17Wang
 * @Date: 19:44 2018/12/18
 */
@Repository
public class CourseDao {
    @Autowired
    private CourseMapper courseMapper;

    @Autowired
    private CClassMapper cClassMapper;

    @Autowired
    private SeminarMapper seminarMapper;

    @Autowired
    private CourseMemberLimitStrategyMapper courseMemberLimitStrategyMapper;

    @Autowired
    private ConflictCourseStrategyMapper conflictCourseStrategyMapper;

    @Autowired
    private ShareSeminarApplicationMapper shareSeminarApplicationMapper;

    @Autowired
    private ShareTeamApplicationMapper shareTeamApplicationMapper;

    /**
     * 组队人数限制策略
     */
    public static final int HAS_COURSE_MEMBER_LIMIT_STRATEGY = 0;
    /**
     * 冲突课程策略
     */
    public static final int HAS_CONFLICT_COURSES = 1;
    public static final int HAS_CCLASS = 2;
    public static final int HAS_SEMINAR = 3;

    private void hasSomethingFun(Course course, int... hasSomething) {
        for (int i : hasSomething) {
            if (i == HAS_COURSE_MEMBER_LIMIT_STRATEGY) {
                CourseMemberLimitStrategy courseMemberLimitStrategy = courseMemberLimitStrategyMapper.getByCourseId(course.getId());
                course.setCourseMemberLimitStrategy(courseMemberLimitStrategy);
            }
            if (i == HAS_CCLASS) {
                course.setcClasses(cClassMapper.findByCourseId(course.getId()));
            }
            if (i == HAS_SEMINAR) {
                course.setSeminars(seminarMapper.findByCourseId(course.getId()));
            }
            if (i == HAS_CONFLICT_COURSES) {
                //TODO 直接查可能会出现重复的课程和课程自己
                List<Course> courses = courseMapper.findAllConflictCourseByNowCourseId(course.getId());
                Map<Long, Course> map = new HashMap<>();
                for (Course conflictCourse : courses) {
                    if (conflictCourse.getId() != course.getId()) {
                        map.put(conflictCourse.getId(), conflictCourse);
                    }
                }
                List<Course> conflictCourses = new ArrayList<>();
                for (Map.Entry<Long, Course> entry : map.entrySet()) {
                    conflictCourses.add(entry.getValue());
                }

                course.setConflictCourses(conflictCourses);
            }
        }
    }

    /**
     * Description: 通过id锁定课程
     *
     * @Author: 17Wang
     * @Time: 19:55 2018/12/18
     */
    public Course getCourseById(long courseId, int... hasSomething) throws MyException {
        Course course = courseMapper.findById(courseId);
        if (course == null) {
            throw new MyException("查找课程失败！不存在该课程", MyException.NOT_FOUND_ERROR);
        }
        hasSomethingFun(course, hasSomething);
        return course;
    }

    public List<Course> listAllCourses(int... hasSomething) {
        List<Course> courses = courseMapper.listAllCourse();
        for (Course course : courses) {
            hasSomethingFun(course, hasSomething);
        }
        return courses;
    }

    /**
     * Description: 创建课程＋创建课程策略
     * 1、添加回滚
     *
     * @Author: 17Wang
     * @Time: 19:50 2018/12/18
     */
    @Transactional(rollbackFor = Exception.class)
    public boolean addCourse(Course course) throws Exception {
        //添加课程
        if (!courseMapper.insertCourse(course)) {
            throw new MyException("创建课程失败！数据库处理错误", MyException.ERROR);
        }
        //组队人数策略
        course.getCourseMemberLimitStrategy().setCourseId(course.getId());
        if (!courseMemberLimitStrategyMapper.insertStrategy(course.getCourseMemberLimitStrategy())) {
            throw new MyException("创建课程策略表失败！数据库处理错误", MyException.ERROR);
        }
        //冲突课程策略
        //TODO 新建课程时冲突课程策略 待测试
        long tableId = conflictCourseStrategyMapper.findMaxId() + 1;
        for (Course conflictCourse : course.getConflictCourses()) {
            conflictCourseStrategyMapper.insertOneLine(tableId, conflictCourse.getId());
        }
        //其他策略

        return true;
    }

    /**
     * Description: 删除课程
     * 1、添加回滚操作
     *
     * @Author: 17Wang
     * @Time: 10:37 2018/12/19
     */
    @Transactional(rollbackFor = MyException.class)
    public boolean deleteCourseById(long courseId) throws Exception {
        //查询是否存在
        getCourseById(courseId);

        //删除冲突课程策略
        //删除冲突课程策略 已测试
        try {
            conflictCourseStrategyMapper.deleteByCourseId(courseId);
        } catch (Exception e) {
            e.printStackTrace();
            throw new MyException("删除冲突课程策略失败", MyException.ERROR);
        }

        //删除策略表
        if (!courseMemberLimitStrategyMapper.deleteByCourseId(courseId)) {
            throw new MyException("删除课程组队策略失败！数据库处理错误", MyException.ERROR);
        }

        //

        //删除课程
        if (!courseMapper.deleteById(courseId)) {
            throw new MyException("删除课程失败！数据库处理错误", MyException.ERROR);
        }

        //TODO 删除share_seminar_application 待测试
        if(!shareSeminarApplicationMapper.deleteByCourseId(courseId)){
            throw new MyException("删除share_seminar_application失败！数据库处理错误",MyException.ERROR );
        }

        //TODO 删除share_team_application 待测试
        if(!shareTeamApplicationMapper.deleteByCourseId(courseId)){
            throw new MyException("删除share_team_application失败！数据库处理错误",MyException.ERROR );
        }

        return true;
    }
}
