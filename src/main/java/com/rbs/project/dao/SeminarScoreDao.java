package com.rbs.project.dao;

import com.rbs.project.exception.MyException;
import com.rbs.project.mapper.*;
import com.rbs.project.pojo.entity.Course;
import com.rbs.project.pojo.entity.SeminarScore;
import com.rbs.project.utils.LogicUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Description:
 * TODO 判断是否为讨论课从课程
 * @Author: 17Wang
 * @Date: 16:11 2018/12/22
 */
@Repository
public class SeminarScoreDao {
    @Autowired
    private SeminarScoreMapper seminarScoreMapper;

    @Autowired
    private CClassSeminarMapper cClassSeminarMapper;

    @Autowired
    private TeamMapper teamMapper;

    @Autowired
    private CourseMapper courseMapper;

    public final static int HAS_TEAM = 0;

    private void hasSomethingFun(SeminarScore seminarScore, int... hasSomething) {
        for (int i : hasSomething) {
            if (i == HAS_TEAM) {
                seminarScore.setTeam(teamMapper.findById(seminarScore.getTeamId()));
            }
        }
    }

    /**
     * Description: 查找一个小组在一个班级下的一个已报名讨论课的分数
     *
     * @Author: 17Wang
     * @Time: 17:43 2018/12/22
     */
    public SeminarScore getSeminarScoreBySeminarIdAndCClassIdAndTeamId(long seminarId, long cClasId, long teamId, int... hasSomething) throws MyException {
        SeminarScore seminarScore = seminarScoreMapper.getBySeminarIdAndCClassIdAndTeamId(seminarId, cClasId, teamId);
        if (seminarScore == null) {
            throw new MyException("查找一个小组在一个班级下的一个已报名讨论课的分数失败！不存在该行", MyException.NOT_FOUND_ERROR);
        }
        hasSomethingFun(seminarScore, hasSomething);
        return seminarScore;
    }

    /**
     * Description:获取一个班级下的一节讨论课的所有展示成绩
     *
     * @Author: 17Wang
     * @Time: 16:19 2018/12/22
     */
    public List<SeminarScore> listAllSeminarScoreBySeminarIdAndCClassId(long seminarId, long cClasId, int... hasSomething) {
        List<SeminarScore> seminarScores = seminarScoreMapper.listAllBySeminarIdAndCClassId(seminarId, cClasId);
        for (SeminarScore seminarScore : seminarScores) {
            hasSomethingFun(seminarScore, hasSomething);

        }
        return seminarScores;
    }

    /**
     * Description: 新增一次展示分数
     *
     * @Author: 17Wang
     * @Time: 21:53 2018/12/21
     */
    public boolean addSeminarScore(long cClassSeminarId, long teamId) throws Exception {
        if (cClassSeminarMapper.findById(cClassSeminarId) == null) {
            throw new MyException("新增一次展示分数错误！该班级的该讨论课不存在", MyException.NOT_FOUND_ERROR);
        }
        if (teamMapper.findById(teamId) == null) {
            throw new MyException("新增一次展示分数！该小组不存在", MyException.NOT_FOUND_ERROR);
        }
        return seminarScoreMapper.insertSeminarScore(cClassSeminarId, teamId);
    }

    /**
     * Description: 通过主键删除展示分数
     *
     * @Author: 17Wang
     * @Time: 22:49 2018/12/21
     */
    public boolean deleteSeminarScoreByPrimaryKey(long cClassSeminarId, long teamId) throws Exception {
        if (!seminarScoreMapper.deleteSeminarScoreByPrimaryKey(cClassSeminarId, teamId)) {
            throw new MyException("通过主键删除展示分数！数据库处理错误", MyException.ERROR);
        }
        return true;
    }

    /**
     * Description: 修改展示的展示分数
     *
     * @Author: 17Wang
     * @Time: 17:47 2018/12/22
     */
    public boolean updatePresentationScore(long seminarId, long classId, long teamId, double presentationScore) throws Exception {
        //检查是否有该行
        SeminarScore seminarScore = getSeminarScoreBySeminarIdAndCClassIdAndTeamId(seminarId, classId, teamId);
        if (!seminarScoreMapper.updatePresentationScore(seminarId, classId, teamId, presentationScore)) {
            throw new MyException("修改展示的展示分数错误！数据库执行错误", MyException.ERROR);
        }
        return true;
    }

    /**
     * Description: 修改展示的报告分数
     *
     * @Author: 17Wang
     * @Time: 17:50 2018/12/22
     */
    public boolean updateReportScore(long seminarId, long classId, long teamId, double reportScore) throws Exception {
        //检查是否有该行
        getSeminarScoreBySeminarIdAndCClassIdAndTeamId(seminarId, classId, teamId);
        if (!seminarScoreMapper.updateReportScore(seminarId, classId, teamId, reportScore)) {
            throw new MyException("修改展示的报告分数错误！数据库执行错误", MyException.ERROR);
        }
        return true;
    }


    /**
     * Description: 修改展示的总分数
     *
     * @Author: 17Wang
     * @Time: 21:03 2018/12/22
     */
    public boolean updateTotalScore(long seminarId, long classId, long teamId, double totalScore) throws Exception {
        //检查是否有该行
        getSeminarScoreBySeminarIdAndCClassIdAndTeamId(seminarId, classId, teamId);
        if (!seminarScoreMapper.updateTotalScore(seminarId, classId, teamId, totalScore)) {
            throw new MyException("修改展示的总分数错误！数据库执行错误", MyException.ERROR);
        }
        return true;
    }

    /**
     * Description: 通过team_id删除轮次成绩
     * @Author: WinstonDeng
     * @Date: 15:00 2018/12/25
     */
    public boolean deleteSemianrScoreByTeamId(long teamId) throws MyException {
        if(!seminarScoreMapper.deleteByTeamId(teamId)){
            throw new MyException("删除讨论课成绩错误！数据库处理错误",MyException.ERROR);
        }
        return true;
    }
}
