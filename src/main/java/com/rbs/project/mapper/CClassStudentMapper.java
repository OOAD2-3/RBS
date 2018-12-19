package com.rbs.project.mapper;

import com.rbs.project.pojo.dto.CClassStudentDTO;
import org.springframework.stereotype.Repository;

/**
 * @Author: WinstonDeng
 * @Description: OOAD_Course_ManageSystem
 * @Date: Created in 16:11 2018/12/19
 * @Modified by:
 */
@Repository
public interface CClassStudentMapper {
    //===============================新增=======================

    /**
     * 新增班级学生
     * @param cClassStudentDTO
     * @return
     */
    boolean insertCClassStudent(CClassStudentDTO cClassStudentDTO);
}
