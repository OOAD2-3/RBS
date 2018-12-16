package com.rbs.project.mapper;

import com.rbs.project.pojo.entity.Student;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Description:
 *
 * @Author: 17Wang
 * @Date: 15:28 2018/12/15
 */
@Repository
public interface StudentMapper {
    /**
     * 通过id锁定一个学生
     *
     * @param id
     * @return
     */
    Student findById(long id);

    /**
     * 通过用户名锁定一个学生
     *
     * @param account
     * @return
     */
    Student findByAccount(String account);

    /**
     * 返回所有学生信息
     *
     * @return 学生名单
     */
    List<Student> listAll();

    /**
     * 通过id，同时修改密码和邮箱
     *
     * @param student
     * @return
     */
    boolean updatePasswordAndEmailAndActiveById(Student student);

    /**
     * 通过id修改密码
     *
     * @param student
     * @return
     */
    boolean updatePasswordById(Student student);

    /**
     * 通过id修改邮箱
     *
     * @param student
     * @return
     */
    boolean updateEmailById(Student student);

    /**
     * 通过id修改激活状态
     *
     * @param student
     * @return
     */
    boolean updateActiveById(Student student);

    /**
     * 新增学生
     * @param student
     * @return
     */
    long insertStudent(Student student);

}
