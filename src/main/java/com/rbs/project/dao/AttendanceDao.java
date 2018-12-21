package com.rbs.project.dao;

import com.rbs.project.mapper.AttendanceMapper;
import com.rbs.project.mapper.TeamMapper;
import com.rbs.project.pojo.entity.Attendance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Description:
 *
 * @Author: 17Wang
 * @Date: 11:36 2018/12/20
 */
@Repository
public class AttendanceDao {
    @Autowired
    private AttendanceMapper attendanceMapper;

    @Autowired
    private TeamMapper teamMapper;

    public static final int HAS_TEAM = 0;

    private void hasSomethingFun(Attendance attendance, int... hasSomething) {
        for (int i : hasSomething) {
            if (i == HAS_TEAM) {
                attendance.setTeam(teamMapper.findById(attendance.getTeamId()));
            }

        }
    }

    /**
     * Description: 获得一个班级下的讨论课的报名信息
     *
     * @Author: 17Wang
     * @Time: 13:01 2018/12/21
     */
    public List<Attendance> listByCClassIdAndSeminarId(long cClassId, long seminarId, int... hasSomething) {
        List<Attendance> attendances=attendanceMapper.findByCClassIdAndSeminarId(cClassId, seminarId);
        for (Attendance attendance:attendances){
            hasSomethingFun(attendance,hasSomething);
        }
        return attendances;
    }
}
