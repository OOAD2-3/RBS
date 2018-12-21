package com.rbs.project.service;

import com.rbs.project.dao.AttendanceDao;
import com.rbs.project.dao.TeamDao;
import com.rbs.project.exception.MyException;
import com.rbs.project.pojo.entity.Attendance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    /**
     * Description: 获取一个班级下的有一个讨论课下的所有报名信息
     * @Author: 17Wang
     * @Time: 11:34 2018/12/20
    */
    public List<Attendance> listAttendanceByCClassIdAndSeminarId(long cClassId,long seminarId) throws MyException {
        List<Attendance> attendances=attendanceDao.listByCClassIdAndSeminarId(cClassId,seminarId);
        for(Attendance attendance:attendances){
            attendance.setTeam(teamDao.getTeamById(attendance.getTeamId(),TeamDao.HAS_CCLASS));
        }
        return attendances;
    }
}
