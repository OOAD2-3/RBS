package com.rbs.project.dao;

import com.rbs.project.exception.MyException;
import com.rbs.project.mapper.AttendanceMapper;
import com.rbs.project.mapper.CClassSeminarMapper;
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

    @Autowired
    private CClassSeminarMapper cClassSeminarMapper;

    public static final int HAS_TEAM = 0;

    public static final int HAS_CLASS_SEMINAR = 1;

    private void hasSomethingFun(Attendance attendance, int... hasSomething) {
        for (int i : hasSomething) {
            if (i == HAS_TEAM) {
                attendance.setTeam(teamMapper.findById(attendance.getTeamId()));
            }
            if (i == HAS_CLASS_SEMINAR) {
                attendance.setcClassSeminar(cClassSeminarMapper.findById(attendance.getcClassSeminarId()));
            }
        }
    }

    /**
     * Description: 通过attendanceId查询一个报名状况失败
     *
     * @Author: 17Wang
     * @Time: 22:39 2018/12/21
     */
    public Attendance getById(long attendanceId, int... hasSomething) throws MyException {
        Attendance attendance = attendanceMapper.findById(attendanceId);
        if (attendance == null) {
            throw new MyException("通过attendanceId查询一个报名状况失败！", MyException.NOT_FOUND_ERROR);
        }
        hasSomethingFun(attendance,hasSomething);
        return attendance;
    }

    /**
     * Description: 获得一个班级下的讨论课的报名信息
     *
     * @Author: 17Wang
     * @Time: 13:01 2018/12/21
     */
    public List<Attendance> listByCClassIdAndSeminarId(long cClassId, long seminarId, int... hasSomething) {
        List<Attendance> attendances = attendanceMapper.findByCClassIdAndSeminarId(cClassId, seminarId);
        for (Attendance attendance : attendances) {
            hasSomethingFun(attendance, hasSomething);
        }
        return attendances;
    }

    /**
     * Description: 新增报名
     *
     * @Author: 17Wang
     * @Time: 20:03 2018/12/21
     */
    public boolean addAttendance(Attendance attendance) {
        return attendanceMapper.insertAttendance(attendance);
    }

    /**
     * Description: 新增一次展示分数
     *
     * @Author: 17Wang
     * @Time: 21:53 2018/12/21
     */
    public boolean addSeminarScore(long cClassSeminarId, long teamId) throws MyException {
        if (cClassSeminarMapper.findById(cClassSeminarId) == null) {
            throw new MyException("新增一次展示分数错误！该班级的该讨论课不存在", MyException.NOT_FOUND_ERROR);
        }
        if (teamMapper.findById(teamId) == null) {
            throw new MyException("新增一次展示分数！该小组不存在", MyException.NOT_FOUND_ERROR);
        }
        return attendanceMapper.insertSeminarScore(cClassSeminarId, teamId);
    }

    /**
     * Description: 通过主键删除展示分数
     * @Author: 17Wang
     * @Time: 22:49 2018/12/21
    */
    public boolean deleteSeminarScoreByPrimaryKey(long cClassSeminarId, long teamId) throws MyException {
        if(! attendanceMapper.deleteSeminarScoreByPrimaryKey(cClassSeminarId,teamId)){
            throw new MyException("通过主键删除展示分数！数据库处理错误", MyException.ERROR);
        }
        return true;
    }

    /**
     * Description: 通过主键删除展示信息
     * @Author: 17Wang
     * @Time: 23:14 2018/12/21
    */
    public boolean deleteAttendanceById(long attendanceId) throws Exception {
        if(!attendanceMapper.deleteAttendanceById(attendanceId)){
            throw new MyException("通过id删除展示信息！数据库处理错误", MyException.ERROR);
        }
        return true;
    }


}