package com.rbs.project.dao;

import com.rbs.project.exception.MyException;
import com.rbs.project.mapper.CClassMapper;
import com.rbs.project.mapper.CourseMapper;
import com.rbs.project.mapper.CourseMemberLimitStrategyMapper;
import com.rbs.project.mapper.SeminarMapper;
import com.rbs.project.pojo.entity.Course;
import com.rbs.project.pojo.strategy.CourseMemberLimitStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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

    /**
     * 组队人数限制策略
     */
    public static final int HAS_COURSE_MEMBER_LIMIT_STRATEGY = 0;
    public static final int HAS_CCLASS=1;
    public static final int HAS_SEMINAR=2;

    private void hasSomethingFun(Course course, int... hasSomething) {
        for (int i : hasSomething) {
            if (i == HAS_COURSE_MEMBER_LIMIT_STRATEGY) {
                CourseMemberLimitStrategy courseMemberLimitStrategy = courseMemberLimitStrategyMapper.getByCourseId(course.getId());
                course.setCourseMemberLimitStrategy(courseMemberLimitStrategy);
            }
            if(i==HAS_CCLASS){
                course.setcClasses(cClassMapper.findByCourseId(course.getId()));
            }
            if(i==HAS_SEMINAR){
                course.setSeminars(seminarMapper.findByCourseId(course.getId()));
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
    public boolean deleteCourseById(long courseId) throws MyException {
        //查询是否存在
        getCourseById(courseId);
        if (!courseMapper.deleteById(courseId)) {
            throw new MyException("删除课程失败！数据库处理错误", MyException.ERROR);
        }
        //删除策略表
        if(!courseMemberLimitStrategyMapper.deleteByCourseId(courseId)){
            throw new MyException("删除课程组队策略失败！数据库处理错误", MyException.ERROR);
        }
        //删除班级

        //删除讨论课

        //删除其他

        return true;
    }
}
