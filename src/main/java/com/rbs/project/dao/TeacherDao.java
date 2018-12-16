package com.rbs.project.dao;

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
     * Description: 获取所有老师信息
     *
     * @Author: 17Wang
     * @Time: 16:50 2018/12/16
     */
    public List<Teacher> listAllTeachers() {
        return teacherMapper.listAll();
    }
}
