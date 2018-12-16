package com.rbs.project.dao;

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
     * Description:获取所有学生信息
     *
     * @Author: 17Wang
     * @Time: 16:50 2018/12/16
     */
    public List<Student> listAllStudents() {
        return studentMapper.listAll();
    }
}
