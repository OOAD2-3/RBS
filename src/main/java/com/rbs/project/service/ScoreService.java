package com.rbs.project.service;

import com.rbs.project.dao.*;
import com.rbs.project.exception.MyException;
import com.rbs.project.pojo.entity.*;
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

    @Autowired
    private StudentDao studentDao;

    @Autowired
    private EmailService emailService;

    @Autowired
    private SeminarDao seminarDao;

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
        //发送邮件
        Seminar seminar = seminarDao.findSeminarById(seminarId);
        Team team = teamDao.getTeamById(teamId);
        String message = "第" + seminar.getSerial() + "节讨论课:" + seminar.getName() + "\n" +
                "小组：" + team.getName() + " 的展示分数已修改为:" + presentationScore + "，总分变为:" + totalScore + "，请注意查看！";
        sendScoreEmail(seminar, message);
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
        //发送邮件
        Seminar seminar = seminarDao.findSeminarById(seminarId);
        Team team = teamDao.getTeamById(teamId);
        String message = "第" + seminar.getSerial() + "节讨论课:" + seminar.getName() + "\n" +
                "小组：" + team.getName() + " 的报告分数已修改为:" + reportScore + "，总分变为:" + totalScore + "，请注意查看！";
        sendScoreEmail(seminar, message);
        return true;
    }

    /**
     * Description: 发送成绩相关邮件
     *
     * @Author: WinstonDeng
     * @Date: 1:44 2018/12/28
     */
    private void sendScoreEmail(Seminar seminar, String message) throws Exception {
        List<Team> teams = teamDao.listByCourseId(seminar.getCourseId());
        for (Team team
                : teams) {
            List<Student> students = studentDao.listByTeamId(team.getId());
            for (Student student
                    : students) {
                emailService.sendEmail(new String[]{student.getEmail()}, message);
            }
        }
    }
}
