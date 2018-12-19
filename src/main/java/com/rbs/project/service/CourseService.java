package com.rbs.project.service;

import com.rbs.project.dao.CourseDao;
import com.rbs.project.dao.UserDao;
import com.rbs.project.exception.MyException;
import com.rbs.project.pojo.entity.Course;
import com.rbs.project.pojo.entity.Teacher;
import com.rbs.project.pojo.entity.User;
import com.rbs.project.utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Description:
 *
 * @Author: 17Wang
 * @Date: 16:13 2018/12/18
 */
@Service
public class CourseService {
    @Autowired
    private CourseDao courseDao;

    @Autowired
    private UserDao userDao;

    /**
     * Description: 创建课程同时创建课程策略内容
     *
     * @Author: 17Wang
     * @Time: 19:45 2018/12/18
     */
    public boolean createCourse(Course course) throws Exception {
        //设置当前教师
        Teacher teacher = (Teacher) UserUtils.getNowUser();
        course.setTeacherId(teacher.getId());
        //设置最大人数
        if (course.getCourseMemberLimitStrategy().getMaxMember() == null) {
            course.getCourseMemberLimitStrategy().setMaxMember(999);
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
        return courseDao.addCourse(course);
    }

    /**
     * Description: 通过id获取当前课程
     * @Author: 17Wang
     * @Time: 22:58 2018/12/18
    */
    public Course getCourseById(long courseId) throws MyException {
        return courseDao.getCourseById(courseId,CourseDao.HAS_COURSE_MEMBER_LIMIT_STRATEGY);
    }

    /**
     * Description: 获取当前用户的所有课程
     *
     * @Author: 17Wang
     * @Time: 22:17 2018/12/18
     */
    public List<Course> listMyCourses() throws MyException {
        //获取当前登录用户的courses
        User user=userDao.getUserByUsername(UserUtils.getNowUser().getUsername(),UserDao.HAS_COURSES);
        return user.getCourses();
    }
    
    /**
     * Description: 删除课程
     * @Author: 17Wang
     * @Time: 10:34 2018/12/19
    */
    public boolean deleteCourseById(long courseId) throws MyException {
        return courseDao.deleteCourseById(courseId);
    }
}