package com.rbs.project.mapper;

import com.rbs.project.pojo.entity.SeminarScore;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Description:
 *
 * @Author: 17Wang
 * @Date: 16:12 2018/12/22
 */
@Mapper
@Repository
public interface SeminarScoreMapper {

    /**
     * 获取一个班级下的一节讨论课的所有展示成绩
     * @param seminarId
     * @param classId
     * @return
     */
    List<SeminarScore> listAllBySeminarIdAndCClassId(@Param("seminarId") long seminarId, @Param("classId") long classId);

    /**
     * 新增一个队伍的一个讨论课分数信息
     *
     * @param cClassSeminarId
     * @param teamId
     * @return
     * @throws Exception
     */
    boolean insertSeminarScore(@Param("cClassSeminarId") long cClassSeminarId, @Param("teamId") long teamId) throws Exception;

    /**
     * 删除一个队伍的一个讨论课分数信息
     *
     * @param cClassSeminarId
     * @param teamId
     * @return
     * @throws Exception
     */
    boolean deleteSeminarScoreByPrimaryKey(@Param("cClassSeminarId") long cClassSeminarId, @Param("teamId") long teamId) throws Exception;
}
