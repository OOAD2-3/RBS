package com.rbs.project.mapper;


import com.rbs.project.pojo.relationship.CClassStudent;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Description:
 *
 * @Author: 17Wang
 * @Date: 13:09 2018/12/19
 */
@Mapper
@Repository
public interface CClassStudentMapper {
    /**
     * 通过主键获取teamId
     *
     * @param cClassId
     * @param studentId
     * @return
     */
    Long getTeamIdByPrimaryKeys(@Param("cClassId") long cClassId, @Param("studentId") long studentId);

    /**
     * 获取一个team下面的所有成员
     * @param teamId
     * @return
     */
    List<Long> getStudentIdByTeamId(long teamId);

    /**
     * 通过主键获取一行
     *
     * @param cClassId
     * @param studentId
     * @return
     */
    Object getByPrimaryKeys(@Param("cClassId") long cClassId, @Param("studentId") long studentId);

    /**
     * 新增班级学生
     * @param cClassStudent
     * @return
     */
    boolean insertCClassStudent(CClassStudent cClassStudent);

    /**
     * 修改学生在这个班级下的所属team
     *
     * @param teamId
     * @param cClassId
     * @param studentId
     * @return
     */
    boolean updateTeamIdByPrimaryKeys(@Param("teamId") long teamId, @Param("cClassId") long cClassId, @Param("studentId") long studentId) throws Exception;
}
