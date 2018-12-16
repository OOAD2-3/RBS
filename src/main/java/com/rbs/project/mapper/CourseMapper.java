package com.rbs.project.mapper;

import com.rbs.project.pojo.entity.Course;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author: WinstonDeng
 * @Description: OOAD_Course_ManageSystem
 * @Date: Created in 14:09 2018/12/16
 * @Modified by:
 */
@Mapper
@Repository
public interface CourseMapper {
    //=========================查找=======================

    /**
     * 通过id找课程
     * @param id
     * @return
     */
    Course findById(long id);

    /**
     * 通过老师查找课程列表
     * @param teacherId
     * @return
     */
    List<Course> findByTeacherId(long teacherId);
    //=========================新增=======================

    /**
     * 新增课程
     * @param course
     * @return
     */
    long insertCourse(Course course);
    //=========================删除=======================

    /**
     * 通过id删除课程
     * @param id
     * @return
     */
    long deleteById(long id);
    //=========================修改=======================
}
