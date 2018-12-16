package com.rbs.project.dao;

import com.rbs.project.exception.MyException;
import com.rbs.project.mapper.StudentMapper;
import com.rbs.project.mapper.TeacherMapper;
import com.rbs.project.pojo.entity.Student;
import com.rbs.project.pojo.entity.Teacher;
import com.rbs.project.pojo.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * Description:
 *
 * @Author: 17Wang
 * @Date: 16:26 2018/12/15
 */
@Repository
public class UserDao {
    @Autowired
    private StudentMapper studentMapper;

    @Autowired
    private TeacherMapper teacherMapper;

    /**
     * Description: 通过id返回学生信息
     *
     * @Author: 17Wang
     * @Time: 12:51 2018/12/16
     */
    public Student getStudentById(long id) throws MyException {
        Student student = studentMapper.findById(id);
        if (student == null) {
            throw new MyException("激活失败！不存在该用户", MyException.NOT_FOUND_ERROR);
        }
        return student;
    }

    /**
     * Description: 通过id返回老师信息
     *
     * @Author: 17Wang
     * @Time: 12:53 2018/12/16
     */
    public Teacher getTeacherById(long id) throws MyException {
        Teacher teacher = teacherMapper.findById(id);
        if (teacher == null) {
            throw new MyException("查询失败！不存在该用户", MyException.NOT_FOUND_ERROR);
        }
        return teacher;
    }

    /**
     * Description: 通过account返回用户信息，先后判断学生和老师
     *
     * @Author: 17Wang
     * @Time: 16:34 2018/12/15
     */
    public User getUserByUsername(String username) {
        User user = studentMapper.findByAccount(username);
        if (user == null) {
            user = teacherMapper.findByAccount(username);
        }
        return user;
    }

    /**
     * Description:激活学生账号时的更新
     *
     * @Author: 17Wang
     * @Time: 12:34 2018/12/16
     */
    public boolean updatePasswordAndEmailAndActiveByStudent(Student student) throws MyException {
        //判断是否存在该id
        try {
            getStudentById(student.getId());
        } catch (MyException e) {
            throw new MyException("激活学生失败！不存在该用户", MyException.NOT_FOUND_ERROR);
        }

        if (!studentMapper.updatePasswordAndEmailAndActiveById(student)) {
            throw new MyException("激活学生失败！数据库处理错误", MyException.ERROR);
        }

        return true;
    }

    /**
     * Description:激活教师号时的更新
     *
     * @Author: 17Wang
     * @Time: 12:34 2018/12/16
     */
    public boolean updatePasswordAndEmailAndActiveByTeacher(Teacher teacher) throws MyException {
        //判断是否存在该id
        try {
            getTeacherById(teacher.getId());
        } catch (MyException e) {
            throw new MyException("激活老师失败！不存在该用户", MyException.NOT_FOUND_ERROR);
        }

        if (!teacherMapper.updatePasswordAndEmailAndActiveById(teacher)) {
            throw new MyException("激活老师失败！数据库处理错误", MyException.ERROR);
        }

        return true;
    }

    /**
     * Description: 学生修改密码
     *
     * @Author: 17Wang
     * @Time: 15:23 2018/12/16
     */
    public boolean updatePasswordByStudent(Student student) throws MyException {
        try {
            getStudentById(student.getId());
        } catch (MyException e) {
            throw new MyException("学生修改密码错误！不存在该用户", MyException.NOT_FOUND_ERROR);
        }

        if (!studentMapper.updatePasswordById(student)) {
            throw new MyException("学生修改密码错误！数据库处理错误", MyException.ERROR);
        }
        return true;
    }

    /**
     * Description: 老师修改密码
     *
     * @Author: 17Wang
     * @Time: 15:26 2018/12/16
     */
    public boolean updatePasswordByTeacher(Teacher teacher) throws MyException {
        try {
            getTeacherById(teacher.getId());
        } catch (MyException e) {
            throw new MyException("老师修改密码错误！不存在该用户", MyException.NOT_FOUND_ERROR);
        }
        if (!teacherMapper.updatePasswordById(teacher)) {
            throw new MyException("老师修改密码错误！数据库处理错误", MyException.ERROR);
        }
        return true;
    }

    /**
     * Description:学生修改邮箱
     *
     * @Author: 17Wang
     * @Time: 16:03 2018/12/16
     */
    public boolean updateEmailByStudent(Student student) throws MyException {
        try {
            getStudentById(student.getId());
        } catch (MyException e) {
            throw new MyException("学生修改邮箱错误！不存在该用户", MyException.NOT_FOUND_ERROR);
        }
        if (!studentMapper.updateEmailById(student)) {
            throw new MyException("学生修改邮箱错误！数据库处理错误", MyException.ERROR);
        }
        return true;
    }

    /**
     * Description: 老师修改邮箱
     * @Author: 17Wang
     * @Time: 16:05 2018/12/16
    */
    public boolean updateEmailByTeacher(Teacher teacher) throws MyException {
        try {
            getTeacherById(teacher.getId());
        } catch (MyException e) {
            throw new MyException("老师修改邮箱错误！不存在该用户", MyException.NOT_FOUND_ERROR);
        }
        if (!teacherMapper.updateEmailById(teacher)) {
            throw new MyException("老师修改邮箱错误！数据库处理错误", MyException.ERROR);
        }
        return true;
    }
}
