package com.rbs.project.dao;

import com.rbs.project.exception.MyException;
import com.rbs.project.mapper.CourseMapper;
import com.rbs.project.mapper.CourseMemberLimitStrategyMapper;
import com.rbs.project.pojo.entity.Course;
import com.rbs.project.pojo.strategy.CourseMemberLimitStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

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
    private CourseMemberLimitStrategyMapper courseMemberLimitStrategyMapper;

    /**
     * Description: 通过id锁定课程
     *
     * @Author: 17Wang
     * @Time: 19:55 2018/12/18
     */
    public Course getCourseById(long courseId) throws MyException {
        Course course = courseMapper.findById(courseId);
        if (course == null) {
            throw new MyException("查找课程失败！不存在该课程", MyException.NOT_FOUND_ERROR);
        }
        return course;
    }

    /**
     * Description: 创建课程＋创建课程策略
     *
     * @Author: 17Wang
     * @Time: 19:50 2018/12/18
     */
    public boolean addCourse(Course course) throws Exception {
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
}
