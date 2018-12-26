package com.rbs.project.mapper;


import com.rbs.project.pojo.relationship.CClassStudent;
import org.apache.ibatis.annotations.Mapper;
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
     * 新增班级学生
     * @param cClassStudent
     * @return
     */
    boolean insertCClassStudent(CClassStudent cClassStudent);


//    /**
//     * 修改学生在这个班级下的所属team
//     *
//     * @param teamId
//     * @param cClassId
//     * @param studentId
//     * @return
//     */
//    boolean updateTeamIdByPrimaryKeys(@Param("teamId") long teamId, @Param("cClassId") long cClassId, @Param("studentId") long studentId) throws Exception;

    /**
     * 批量修改team_id为null
     * @param teamId
     * @return
     * @throws Exception
     */
    boolean updateTeamIdCollectionToNull(long teamId) throws Exception;

}
