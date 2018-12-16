package com.rbs.project.mapper;

import com.rbs.project.pojo.entity.Student;
import com.rbs.project.pojo.entity.Teacher;
import org.springframework.stereotype.Repository;

/**
 * Description:
 *
 * @Author: 17Wang
 * @Date: 16:37 2018/12/15
 */
@Repository
public interface TeacherMapper {
    /**
     * 通过id锁定一个老师
     *
     * @param id
     * @return
     */
    Teacher findById(int id);

    /**
     * 通过用户名锁定一个老师
     *
     * @param account
     * @return
     */
    Teacher findByAccount(String account);

    /**
     * 通过id，同时修改密码和邮箱
     *
     * @param teacher
     * @return
     */
    boolean updatePasswordAndEmailAndActiveById(Teacher teacher);

    /**
     * 通过id修改密码
     *
     * @param teacher
     * @return
     */
    boolean updatePasswordById(Teacher teacher);

    /**
     * 通过id修改邮箱
     *
     * @param teacher
     * @return
     */
    boolean updateEmailById(Teacher teacher);

    /**
     * 通过id修改激活状态
     *
     * @param teacher
     * @return
     */
    boolean updateActiveById(Teacher teacher);
}
