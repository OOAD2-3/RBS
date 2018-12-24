package com.rbs.project.service;

import com.rbs.project.dao.*;
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
    private CourseDao courseDao;

    @Autowired
    private TeamDao teamDao;

    @Autowired
    private StudentDao studentDao;

    @Autowired
    private CClassStudentDao cClassStudentDao;

    /**
     * Description: 新建一个Team
     *
     * @Author: 17Wang
     * @Time: 11:53 2018/12/19
     */
    @Transactional(rollbackFor = Exception.class)
    public Long createTeam(Team team) throws Exception {
        if (teamDao.getTeamByLeaderId(team.getLeaderId()) != null) {
            throw new MyException("创建小组出错！这个人已经在这个课程下创建了一个小组", MyException.ERROR);
        }
        if (cClassStudentDao.getTeamIdByPrimaryKeys(team.getcClassId(), team.getLeaderId()) != 0) {
            throw new MyException("创建小组出错！这个人已经在这个课程下加入了一个小组", MyException.ERROR);
        }
        for(Student student:team.getStudents()){
            if (cClassStudentDao.getTeamIdByPrimaryKeys(team.getcClassId(), student.getId()) != 0) {
                throw new MyException("添加成员出错！学生"+student.getId()+"已有队伍", MyException.ERROR);
            }
        }
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

        //给team设置班级的策略
        Course course = courseDao.getCourseById(team.getCourseId(), CourseDao.HAS_COURSE_MEMBER_LIMIT_STRATEGY);
        team.setCourse(course);
        //判断队伍是否合法
        if (LogicUtils.teamIsValid(team)) {
            team.setStatus(Team.STATUS_OK);
        } else {
            team.setStatus(Team.STATUS_ERROR);
        }

        //插入 team表
        teamDao.addTeam(team);

        //修改klass_Student
        for (Student student : team.getStudents()) {
            cClassStudentDao.updateTeamIdInKlassStudent(team.getId(), team.getcClassId(), student.getId());
        }
        cClassStudentDao.updateTeamIdInKlassStudent(team.getId(), team.getcClassId(), team.getLeaderId());
        //返回team的id
        return team.getId();
    }

    /**
     * Description: 通过teamid锁定一个team
     *
     * @Author: 17Wang
     * @Time: 22:00 2018/12/19
     */
    public Team getTeamById(long teamId, int... hasSomething) throws MyException {
        if (hasSomething.length == 0) {
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
        return teamDao.getTeamById(teamId, hasSomething);
    }

    /**
     * Description:获取课程下的所有小组
     *
     * @Author: 17Wang
     * @Time: 11:38 2018/12/23
     */
    public List<Team> listTeamByCourseId(long courseId) throws MyException {
        return teamDao.listByCourseId(courseId, TeamDao.HAS_CCLASS);
    }

    /**
     * Description:
     *
     * @Author: 17Wang
     * @Time: 11:48 2018/12/23
     */
    public Team getTeamByCourseIdAndStudentId(long courseId, long studentId) throws MyException {
        Team team = teamDao.getTeamByCourseIdAndStudentId(courseId, studentId,
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
            if (cClassStudentDao.getTeamIdByPrimaryKeys(cClassId, i) != 0) {
                throw new MyException("添加成员出错！学生已有队伍", MyException.ERROR);
            }
            cClassStudentDao.updateTeamIdInKlassStudent(teamId, cClassId, i);
        }
        //小组状态判断和修改
        Team team = teamDao.getTeamById(teamId, TeamDao.HAS_MEMBERS);

        //给team设置班级的策略
        Course course = courseDao.getCourseById(team.getCourseId(), CourseDao.HAS_COURSE_MEMBER_LIMIT_STRATEGY);
        team.setCourse(course);
        if (LogicUtils.teamIsValid(team)) {
            teamDao.updateStatusByTeamId(Team.STATUS_OK, teamId);
        } else {
            teamDao.updateStatusByTeamId(Team.STATUS_ERROR, teamId);
        }

        return true;
    }

    /**
     * Description: 删除成员
     *
     * @Author: 17Wang
     * @Time: 9:12 2018/12/20
     */
    @Transactional(rollbackFor = Exception.class)
    public boolean removeMemberFromTeam(long teamId, long memberId) throws Exception {
        //确认组长所在的班级
        long cClassId = teamDao.getTeamById(teamId).getcClassId();
        //确认被踢的成员当前是否还在队伍
        long memberAtTeamId = cClassStudentDao.getTeamIdByPrimaryKeys(cClassId, memberId);
        if (memberAtTeamId == 0) {
            throw new MyException("删除成员出错！该成员已被踢出队伍", MyException.ERROR);
        } else if (memberAtTeamId != teamId) {
            throw new MyException("删除成员出错！该成员不属于您的小组", MyException.ERROR);
        }
        //上述判断通过，将该成员踢出
        cClassStudentDao.updateTeamIdInKlassStudent(0, cClassId, memberId);
        //小组状态判断和修改
        Team team = teamDao.getTeamById(teamId, TeamDao.HAS_MEMBERS);
        //给team设置班级的策略
        Course course = courseDao.getCourseById(team.getCourseId(), CourseDao.HAS_COURSE_MEMBER_LIMIT_STRATEGY);
        team.setCourse(course);
        if (LogicUtils.teamIsValid(team)) {
            teamDao.updateStatusByTeamId(Team.STATUS_OK, teamId);
        } else {
            teamDao.updateStatusByTeamId(Team.STATUS_ERROR, teamId);
        }

        return true;
    }

    /**
     * Description: 解散小组
     *
     * @Author: 17Wang
     * @Time: 10:45 2018/12/23
     */
    @Transactional(rollbackFor = Exception.class)
    public boolean dissolveTeam(long teamId) throws Exception {
        //将该小组下的成员置为无小组状态
        List<Long> studentIds = cClassStudentDao.getStudentIdByTeamId(teamId);
        for (Long l : studentIds) {
            removeMemberFromTeam(teamId, l);
        }
        //删除这个小组
        teamDao.deleteTeamById(teamId);
        return true;
    }
}
