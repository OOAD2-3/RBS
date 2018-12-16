package com.rbs.project.service;

import com.rbs.project.dao.TeacherDao;
import com.rbs.project.pojo.entity.Teacher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Description:
 *
 * @Author: 17Wang
 * @Date: 16:51 2018/12/16
 */
@Service
public class TeacherService {
    @Autowired
    private TeacherDao teacherDao;

    /**
     * Description: 获取所有老师信息
     * @Author: 17Wang
     * @Time: 16:52 2018/12/16
    */
    public List<Teacher> listAllTeachers(){
        return teacherDao.listAllTeachers();
    }
}
