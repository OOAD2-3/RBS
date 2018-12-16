package com.rbs.project.service;

import com.rbs.project.dao.StudentDao;
import com.rbs.project.pojo.entity.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Description:
 *
 * @Author: 17Wang
 * @Date: 16:37 2018/12/16
 */
@Service
public class StudentService {
    @Autowired
    private StudentDao studentDao;

    /**
     * Description: 获取所有学生信息
     * @Author: 17Wang
     * @Time: 16:51 2018/12/16
    */
    public List<Student> listAllStudents(){
        return studentDao.listAllStudents();
    }
}
