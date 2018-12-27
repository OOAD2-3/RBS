package com.rbs.project.mapper;

import com.rbs.project.pojo.entity.SeminarScore;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.security.core.parameters.P;
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
     * 查找一个小组在一个班级下的一个已报名讨论课的分数
     *
     * @param seminarId
     * @param classId
     * @param teamId
     * @return
     */
    SeminarScore getBySeminarIdAndCClassIdAndTeamId(@Param("seminarId") long seminarId, @Param("classId") long classId, @Param("teamId") long teamId);

    /**
     * 获取一个班级下的一节讨论课的所有展示成绩
     *
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

    /**
     * 修改展示的展示分数
     *
     * @param seminarId
     * @param classId
     * @param teamId
     * @param presentationScore
     * @return
     * @throws Exception
     */
    boolean updatePresentationScore(@Param("seminarId") long seminarId, @Param("classId") long classId,
                                    @Param("teamId") long teamId, @Param("presentationScore") double presentationScore) throws Exception;

    /**
     * 修改展示的报告分数
     *
     * @param seminarId
     * @param classId
     * @param teamId
     * @param reportScore
     * @return
     * @throws Exception
     */
    boolean updateReportScore(@Param("seminarId") long seminarId, @Param("classId") long classId,
                              @Param("teamId") long teamId, @Param("reportScore") double reportScore) throws Exception;

    /**
     * 修改展示的总分数
     *
     * @param seminarId
     * @param classId
     * @param teamId
     * @param totalScore
     * @return
     * @throws Exception
     */
    boolean updateTotalScore(@Param("seminarId") long seminarId, @Param("classId") long classId,
                             @Param("teamId") long teamId, @Param("totalScore") double totalScore) throws Exception;

    /**
     * 通过team_id删除记录
     * @param teamId
     * @return
     */
    boolean deleteByTeamId(long teamId);

    /**
     * 通过班级讨论课删除轮次成绩
     * @param cClassSeminarId
     * @return
     */
    boolean deleteByCClassSeminarId(long cClassSeminarId);
}
