package com.rbs.project.service;

import com.rbs.project.dao.*;
import com.rbs.project.exception.MyException;
import com.rbs.project.pojo.entity.Course;
import com.rbs.project.pojo.entity.RoundScore;
import com.rbs.project.pojo.entity.SeminarScore;
import com.rbs.project.utils.LogicUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Description:
 *
 * @Author: 17Wang
 * @Date: 16:15 2018/12/22
 */
@Service
public class ScoreService {
    @Autowired
    private SeminarScoreDao seminarScoreDao;

    @Autowired
    private RoundScoreDao roundScoreDao;

    @Autowired
    private TeamDao teamDao;

    @Autowired
    private CClassDao cClassDao;

    /**
     * Description: 获取一个班级下的一节讨论课的所有展示成绩
     *
     * @Author: 17Wang
     * @Time: 16:19 2018/12/22
     */
    public List<SeminarScore> listAllSeminarScoreBySeminarIdAndCClassId(long semianrId, long cClassId) throws MyException {
        List<SeminarScore> seminarScores = seminarScoreDao.listAllSeminarScoreBySeminarIdAndCClassId(semianrId, cClassId);
        //带上有班级信息的team
        for (SeminarScore seminarScore : seminarScores) {
            seminarScore.setTeam(teamDao.getTeamById(seminarScore.getTeamId(), TeamDao.HAS_CCLASS));
        }
        return seminarScores;
    }

    /**
     * Description: 获取一个轮次下所有队伍的讨论课的成绩
     *
     * @Author: 17Wang
     * @Time: 17:02 2018/12/22
     */
    public List<RoundScore> listAllRoundScoreByRoundId(long roundId) throws MyException {
        List<RoundScore> roundScores = roundScoreDao.listAllRoundScoreByRoundId(roundId);
        for (RoundScore roundScore : roundScores) {
            roundScore.setTeam(teamDao.getTeamById(roundScore.getTeamId(), TeamDao.HAS_CCLASS));
        }
        return roundScores;
    }

    /**
     * Description: 修改展示的展示分数
     *
     * @Author: 17Wang
     * @Time: 17:37 2018/12/22
     */
    @Transactional(rollbackFor = Exception.class)
    public boolean updatePresentationScore(long seminarId, long classId, long teamId, double presentationScore) throws Exception {
        seminarScoreDao.updatePresentationScore(seminarId, classId, teamId, presentationScore);
        //修改总分
        SeminarScore seminarScore = seminarScoreDao.getSeminarScoreBySeminarIdAndCClassIdAndTeamId(seminarId, classId, teamId);
        double totalScore = LogicUtils.calculateSeminarTotalScore(seminarScore, cClassDao.getById(classId, CClassDao.HAS_COURSE).getCourse());
        seminarScoreDao.updateTotalScore(seminarId, classId, teamId, totalScore);
        return true;
    }

    /**
     * Description: 修改展示的报告分数
     *
     * @Author: 17Wang
     * @Time: 20:18 2018/12/22
     */
    @Transactional(rollbackFor = Exception.class)
    public boolean updateReportScore(long seminarId, long classId, long teamId, double reportScore) throws Exception {
        seminarScoreDao.updateReportScore(seminarId, classId, teamId, reportScore);
        //修改总分
        SeminarScore seminarScore = seminarScoreDao.getSeminarScoreBySeminarIdAndCClassIdAndTeamId(seminarId, classId, teamId);
        double totalScore = LogicUtils.calculateSeminarTotalScore(seminarScore, cClassDao.getById(classId, CClassDao.HAS_COURSE).getCourse());
        seminarScoreDao.updateTotalScore(seminarId, classId, teamId, totalScore);
        return true;
    }
}
