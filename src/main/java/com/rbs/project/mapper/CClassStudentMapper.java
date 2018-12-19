package com.rbs.project.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

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
     * 通过主键获取一行
     *
     * @param cClassId
     * @param studentId
     * @return
     */
    Object getByPrimaryKeys(@Param("cClassId") long cClassId, @Param("studentId") long studentId);

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
