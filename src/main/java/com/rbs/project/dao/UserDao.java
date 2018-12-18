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


}
