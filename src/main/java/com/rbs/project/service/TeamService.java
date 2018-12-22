package com.rbs.project.service;

import com.rbs.project.dao.CClassDao;
import com.rbs.project.dao.StudentDao;
import com.rbs.project.dao.TeamDao;
import com.rbs.project.exception.MyException;
import com.rbs.project.mapper.StudentMapper;
import com.rbs.project.pojo.entity.CClass;
import com.rbs.project.pojo.entity.Course;
import com.rbs.project.pojo.entity.Student;
import com.rbs.project.pojo.entity.Team;
import com.rbs.project.utils.LogicUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * Description:
 *
 * @Author: 17Wang
 * @Date: 11:13 2018/12/19
 */
@Service
public class TeamService {
    @Autowired
    private TeamDao teamDao;

    @Autowired
    private CClassDao cClassDao;

    @Autowired
    private StudentDao studentDao;

    /**
     * Description: 新建一个Team
     *
     * @Author: 17Wang
     * @Time: 11:53 2018/12/19
     */
    @Transactional(rollbackFor = Exception.class)
    public boolean createTeam(Team team) throws Exception {
        //team Serial
        List<Team> teams = teamDao.listByCClassId(team.getcClassId());

        int teamSerial = 1;
        for (; ; teamSerial++) {
            boolean flag = false;
            for (Team existTeam : teams) {
                if (existTeam.getSerial() == teamSerial) {
                    flag = true;
                    break;
                }
            }
            if (!flag) {
                break;
            }
        }
        team.setSerial(teamSerial);

        //判断队伍是否合法
        if (LogicUtils.TeamIsValid(team)) {
            team.setStatus(Team.STATUS_OK);
        } else {
            team.setStatus(Team.STATUS_ERROR);
        }

        //插入 team表
        teamDao.addTeam(team);

        //修改klass_Student
        for (Student student : team.getStudents()) {
            cClassDao.updateTeamIdInKlassStudent(team.getId(), team.getcClassId(), student.getId());
        }
        cClassDao.updateTeamIdInKlassStudent(team.getId(), team.getcClassId(), team.getLeaderId());
        return true;
    }

    /**
     * Description: 通过teamid锁定一个team
     *
     * @Author: 17Wang
     * @Time: 22:00 2018/12/19
     */
    public Team getTeamById(long teamId) throws MyException {
        Team team = teamDao.getTeamById(teamId,
                TeamDao.HAS_COURSE,
                TeamDao.HAS_CCLASS,
                TeamDao.HAS_LEADER,
                TeamDao.HAS_MEMBERS);
        if (team.getCourse() == null) {
            team.setCourse(new Course());
        }
        if (team.getcClass() == null) {
            team.setcClass(new CClass());
        }
        if (team.getLeader() == null) {
            team.setLeader(new Student());
        }
        if (team.getStudents() == null) {
            team.setStudents(new ArrayList<>());
        }
        return team;
    }

    /**
     * Description: 修改小组状态，暂时不用
     *
     * @Author: 17Wang
     * @Time: 22:55 2018/12/19
     */
    public boolean updateTeam(Team team) {
        return false;
    }

    /**
     * Description: 添加成员
     *
     * @Author: 17Wang
     * @Time: 23:08 2018/12/19
     */
    @Transactional(rollbackFor = Exception.class)
    public boolean addMemberToTeam(long teamId, List<Long> membersIds) throws Exception {
        if (membersIds.isEmpty()) {
            throw new MyException("没有需要添加的成员或者参数名写错了，是studentId哦", MyException.ID_FORMAT_ERROR);
        }
        long cClassId = teamDao.getTeamById(teamId).getcClassId();
        for (Long i : membersIds) {
            ///如果没有该学生
            studentDao.getStudentById(i);
            //如果该学生已有队伍
            if (cClassDao.getTeamIdByPrimaryKeys(cClassId, i) != 0) {
                throw new MyException("添加成员出错！学生已有队伍", MyException.ERROR);
            }
            cClassDao.updateTeamIdInKlassStudent(teamId, cClassId, i);
        }
        return true;
    }

    /**
     * Description: 删除成员
     *
     * @Author: 17Wang
     * @Time: 9:12 2018/12/20
     */
    public boolean removeMemberFromTeam(long teamId, long memberId) throws Exception {
        //确认组长所在的班级
        long cClassId = teamDao.getTeamById(teamId).getcClassId();
        //确认被踢的成员当前是否还在队伍
        long memberAtTeamId = cClassDao.getTeamIdByPrimaryKeys(cClassId, memberId);
        if (memberAtTeamId == 0) {
            throw new MyException("删除成员出错！该成员已被踢出队伍", MyException.ERROR);
        } else if (memberAtTeamId != teamId) {
            throw new MyException("删除成员出错！该成员不属于您的小组", MyException.ERROR);
        }
        //上述判断通过，将该成员踢出
        return cClassDao.updateTeamIdInKlassStudent(0, cClassId, memberId);
    }
}
