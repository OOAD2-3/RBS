package com.rbs.project.service;

import com.rbs.project.dao.*;
import com.rbs.project.exception.MyException;
import com.rbs.project.pojo.entity.Attendance;
import com.rbs.project.pojo.entity.CClassSeminar;
import com.rbs.project.pojo.entity.RoundScore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Description:
 *
 * @Author: 17Wang
 * @Date: 11:04 2018/12/20
 */
@Service
public class AttendanceService {
    @Autowired
    private AttendanceDao attendanceDao;

    @Autowired
    private TeamDao teamDao;

    @Autowired
    private CClassSeminarDao cClassSeminarDao;

    @Autowired
    private SeminarDao seminarDao;

    @Autowired
    private RoundScoreDao roundScoreDao;

    @Autowired
    private SeminarScoreDao seminarScoreDao;

    /**
     * Description: 获取一个班级下的有一个讨论课下的所有报名信息
     *
     * @Author: 17Wang
     * @Time: 11:34 2018/12/20
     */
    public List<Attendance> listAttendanceByCClassIdAndSeminarId(long cClassId, long seminarId) throws MyException {
        List<Attendance> attendances = attendanceDao.listByCClassIdAndSeminarId(cClassId, seminarId, AttendanceDao.HAS_CLASS_SEMINAR);
        for (Attendance attendance : attendances) {
            attendance.setTeam(teamDao.getTeamById(attendance.getTeamId(), TeamDao.HAS_CCLASS));
        }
        return attendances;
    }

    /**
     * Description: 报名一节讨论课
     *
     * @Author: 17Wang
     * @Time: 19:39 2018/12/21
     */
    @Transactional(rollbackFor = Exception.class)
    public boolean addAttendance(long teamId, int teamOrder, long cClassId, long seminarId) throws Exception {
        //报名检查
        List<Attendance> attendances = attendanceDao.listByCClassIdAndSeminarId(cClassId, seminarId);
        for (Attendance attendance : attendances) {
            if (attendance.getTeamId() == teamId) {
                throw new MyException("报名讨论课失败！该队已报名过了该节讨论课", MyException.ERROR);
            }
            if (attendance.getTeamOrder() == teamOrder) {
                throw new MyException("报名讨论课失败！该位置已被其他队伍报名", MyException.ERROR);
            }
        }

        //主要查找到cClassSeminar的Id
        Long cClassSeminarId = cClassSeminarDao.findCClassSeminarByCClassIdAndSeminarId(cClassId, seminarId).getId();
        Attendance attendance = new Attendance();
        attendance.setcClassSeminarId(cClassSeminarId);
        attendance.setTeamId(teamId);
        attendance.setTeamOrder(teamOrder);

        //设置状态
        attendance.setPresent(Attendance.PRESENT_NO_START);
        attendanceDao.addAttendance(attendance);

        //新增roundScore
        long roundId = seminarDao.findSeminarById(seminarId).getRoundId();
        RoundScore roundScore = new RoundScore();
        roundScore.setRoundId(roundId);
        roundScore.setTeamId(teamId);
        roundScoreDao.addRoundScore(roundScore);

        //新增seminarScore
        seminarScoreDao.addSeminarScore(cClassSeminarId, teamId);

        return true;
    }

    /**
     * Description: 取消报名
     *
     * @Author: 17Wang
     * @Time: 22:33 2018/12/21
     */
    @Transactional(rollbackFor = Exception.class)
    public boolean deleteAttendance(long attendanceId) throws Exception {
        //查找有没有这个
        Attendance attendance = attendanceDao.getById(attendanceId);
        System.out.println(attendance);
        long cClassSeminarId = attendance.getcClassSeminarId();
        long teamId = attendance.getTeamId();
        long seminarId=cClassSeminarDao.getCClassSeminarById(cClassSeminarId).getSeminarId();
        long roundId = seminarDao.findSeminarById(seminarId).getRoundId();

        //删除讨论课成绩
        seminarScoreDao.deleteSeminarScoreByPrimaryKey(cClassSeminarId, teamId);

        //删除轮次成绩
        roundScoreDao.deleteRoundScoreByPrimaryKey(roundId,teamId);

        //删除报名
        attendanceDao.deleteAttendanceById(attendanceId);

        return true;
    }
}
