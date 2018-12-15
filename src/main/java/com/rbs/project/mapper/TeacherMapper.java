package com.rbs.project.mapper;

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
     * @param id
     * @return
     */
    Teacher findById(int id);

    /**
     * 通过用户名锁定一个老师
     * @param account
     * @return
     */
    Teacher findByAccount(String account);
}
