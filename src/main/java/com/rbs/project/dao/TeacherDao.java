package com.rbs.project.dao;

import com.rbs.project.exception.MyException;
import com.rbs.project.mapper.TeacherMapper;
import com.rbs.project.pojo.entity.Teacher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Description:
 *
 * @Author: 17Wang
 * @Date: 16:49 2018/12/16
 */
@Repository
public class TeacherDao {
    @Autowired
    private TeacherMapper teacherMapper;

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
     * Description: 获取所有老师信息
     *
     * @Author: 17Wang
     * @Time: 16:50 2018/12/16
     */
    public List<Teacher> listAllTeachers() {
        return teacherMapper.listAll();
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
