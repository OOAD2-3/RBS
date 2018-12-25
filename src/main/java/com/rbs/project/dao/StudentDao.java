package com.rbs.project.dao;

import com.rbs.project.exception.MyException;
import com.rbs.project.mapper.StudentMapper;
import com.rbs.project.pojo.entity.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Description:
 *
 * @Author: 17Wang
 * @Date: 16:46 2018/12/16
 */
@Repository
public class StudentDao {
    @Autowired
    private StudentMapper studentMapper;

    /**
     * Description: 通过id返回学生信息
     *
     * @Author: 17Wang
     * @Time: 12:51 2018/12/16
     */
    public Student getStudentById(long id) throws MyException {
        Student student = studentMapper.findById(id);
//        if (student == null) {
//            throw new MyException("激活失败！不存在该用户", MyException.NOT_FOUND_ERROR);
//        }
        return student;
    }

    /**
     * Description: 获取一个课程下没有组队的所有学生
     *
     * @Author: 17Wang
     * @Time: 13:17 2018/12/23
     */
    public List<Student> listByCourseIdAndTeamIdIsNULL(long courseId) {
        return studentMapper.findByCourseIdAndTeamIdIsNULL(courseId);
    }

    /**
     * Description: 获取一个team下面的所有成员
     * @Author: 17Wang
     * @Time: 22:15 2018/12/25
    */
    public List<Student> listByTeamId(long teamId){
        return studentMapper.findByTeamId(teamId);
    }

    /**
     * Description:获取所有学生信息
     *
     * @Author: 17Wang
     * @Time: 16:50 2018/12/16
     */
    public List<Student> listAllStudents() {
        return studentMapper.listAll();
    }

    /**
     * Description: 通过学号查找一个学生
     *
     * @Author: 17Wang
     * @Time: 23:08 2018/12/16
     */
    public Student getStudentByAccount(String account) throws MyException {
        Student student = studentMapper.findByAccount(account);
        if (student == null) {
            throw new MyException("通过学号查找学生出错！", MyException.NOT_FOUND_ERROR);
        }
        return student;
    }

    /**
     * Description: 通过学生名查找一个学生
     *
     * @Author: 17Wang
     * @Time: 23:14 2018/12/16
     */
    public Student getStudentByStudentName(String studentName) throws MyException {
        Student student = studentMapper.findByStudentName(studentName);
        if (student == null) {
            throw new MyException("通过学生名查找学生出错", MyException.NOT_FOUND_ERROR);
        }
        return student;
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
     * Description: 学生修改名字
     *
     * @Author: 17Wang
     * @Time: 23:34 2018/12/16
     */
    public boolean updateStudentNameByStudent(Student student) throws MyException {
        try {
            getStudentById(student.getId());
        } catch (MyException e) {
            throw new MyException("学生修改名字错误！不存在该用户", MyException.NOT_FOUND_ERROR);
        }

        if (!studentMapper.updateStudentNameById(student)) {
            throw new MyException("学生修改名字错误！数据库处理错误", MyException.ERROR);
        }
        return true;
    }

    /**
     * Description: 学生修改学号
     *
     * @Author: 17Wang
     * @Time: 23:47 2018/12/16
     */
    public boolean updateAccountByStudent(Student student) throws MyException {
        try {
            getStudentById(student.getId());
        } catch (MyException e) {
            throw new MyException("学生修改学号错误！不存在该用户", MyException.NOT_FOUND_ERROR);
        }

        if (!studentMapper.updateAccountById(student)) {
            throw new MyException("学生修改学号错误！数据库处理错误", MyException.ERROR);
        }
        return true;
    }

    public boolean deleteStudentByStudentId(long studentId) throws MyException {
        try {
            getStudentById(studentId);
        } catch (MyException e) {
            throw new MyException("删除学生错误！不存在该用户", MyException.NOT_FOUND_ERROR);
        }

        if (!studentMapper.deleteStudentById(studentId)) {
            throw new MyException("删除学生错误！数据库处理错误", MyException.ERROR);
        }
        return true;

    }

    /**
     * Description: 新增学生
     *
     * @Author: WinstonDeng
     * @Date: 0:04 2018/12/17
     */
    public long addStudent(Student student) throws MyException {
        long studentId = -1;
        if (studentMapper.findByAccount(student.getUsername()) == null) {
            try {
                studentMapper.insertStudent(student);
                studentId = student.getId();
            } catch (Exception e) {
                throw new MyException("新增学生错误！数据库处理错误", MyException.ERROR);
            }
        }
        return studentId;
    }
}
