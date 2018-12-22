package com.rbs.project.mapper;

import com.rbs.project.pojo.entity.CClass;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import java.util.List;

/**
 * @Author: WinstonDeng
 * @Description: OOAD_Course_ManageSystem
 * @Date: Created in 12:32 2018/12/16
 * @Modified by:
 */
@Mapper
@Repository
public interface CClassMapper {
    //=================查找====================

    /**
     * 通过id查找班级
     * @param id
     * @return
     */
    CClass findById(long id);

    /**
     * 通过课程查找班级列表
     * @param courseId
     * @return
     */
    List<CClass> findByCourseId(long courseId);

    /**
     * 通过讨论课Id获取班级信息
     * @param seminarId
     * @return
     */
    CClass findBySeminarId(long seminarId);

    //=================新增====================

    /**
     * 新增班级
     * @param cClass
     * @return
     */
    boolean insertCClass(CClass cClass);

    //=================删除====================

    /**
     * 通过id删除班级
     * @param id
     * @return
     */
    boolean deleteCClassById(long id);

}
