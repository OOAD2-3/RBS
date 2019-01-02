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

    /**
     * 批量修改team_id为null
     * @param teamId
     * @return
     * @throws Exception
     */
    boolean updateTeamIdCollectionToNull(long teamId) throws Exception;

    /**
     * 通过学生id 和 课程id 确认该记录是否存在，仅用于共享
     * @param studentId
     * @param courseId
     * @return
     * @throws Exception
     */
    CClassStudent getByIdAndCourseId(long studentId, long courseId) throws Exception;
}
