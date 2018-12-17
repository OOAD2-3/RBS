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
    /**
     * Description: 新增学生
     * @Author: WinstonDeng
     * @Date: 0:04 2018/12/17
     */
    public long addStudent(Student student) throws MyException{
        long studentId= -1;
        if(studentMapper.findByAccount(student.getUsername())==null){
            try{
                studentMapper.insertStudent(student);
                studentId=student.getId();
            }catch (Exception e){
                throw new MyException("新增学生错误！数据库处理错误",MyException.ERROR);
            }
        }
        return studentId;
    }
}
