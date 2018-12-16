package com.rbs.project.mapper;

import com.rbs.project.pojo.entity.CClassSeminar;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author: WinstonDeng
 * @Description: OOAD_Course_ManageSystem
 * @Date: Created in 18:56 2018/12/16
 * @Modified by:
 */
@Mapper
@Repository
public interface CClassSeminarMapper {
    //===============================查找=========================

    /**
     * 通过id找班级讨论课
     * @param id
     * @return
     */
    CClassSeminar findById(long id);

    /**
     * 通过班级找班级讨论课列表
     * @param cClassId
     * @return
     */
    List<CClassSeminar> findByCClassId(long cClassId);

    /**
     * 通过班级和讨论课查找班级讨论课
     * @param cClassId
     * @param seminarId
     * @return
     */
    CClassSeminar findByCClassIdAndSeminarId(@Param("cClassId") long cClassId,@Param("seminarId") long seminarId);
}
