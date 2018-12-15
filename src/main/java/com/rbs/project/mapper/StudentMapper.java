package com.rbs.project.mapper;

import com.rbs.project.pojo.entity.Student;
import org.springframework.stereotype.Repository;

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
     * @param id
     * @return
     */
    Student findById(int id);
}
