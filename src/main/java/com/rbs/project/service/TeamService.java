package com.rbs.project.service;

import com.rbs.project.dao.*;
import com.rbs.project.exception.MyException;
import com.rbs.project.pojo.entity.CClass;
import com.rbs.project.pojo.entity.Course;
import com.rbs.project.pojo.entity.Student;
import com.rbs.project.pojo.entity.Team;
import com.rbs.project.utils.LogicUtils;
import com.rbs.project.utils.UserUtils;
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
    private CClassDao cClassDao;

    /**
     * Description: 新建一个Team
     *
     * @Author: 17Wang
     * @Time: 11:53 2018/12/19
     */
    @Transactional(rollbackFor = Exception.class)
    public Long createTeam(Team team) throws Exception {
        //如果出错，说明这个leader已经在这个课程下有了小组
        try {
            teamDao.getTeamBycClassIdAndStudentId(team.getcClassId(), team.getLeaderId());
        } catch (Exception e) {
            throw new MyException("创建小组出错！这个人已经在这个课程下加入了一个小组", MyException.ERROR);
        }
        for (Student student : team.getStudents()) {
            //如果出错，说明有成员已经在这个课程下有了小组
            try {
                teamDao.getTeamBycClassIdAndStudentId(team.getcClassId(), student.getId());
            } catch (Exception e) {
                throw new MyException("添加成员出错！学生" + student.getId() + "已有队伍", MyException.ERROR);
            }
        }
        //team Serial
        //已改连表，标识一下，做到这里了
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
        //在这个方法同时插入到klass_team中
        teamDao.addTeam(team);

        //修改team_student 新表
        for (Student student : team.getStudents()) {
            teamDao.addTeamStudentByTeamIdAndStudentId(team.getId(), student.getId());
        }
        teamDao.addTeamStudentByTeamIdAndStudentId(team.getId(), team.getLeaderId());

        //返回team的id
        return team.getId();
    }

    /**
     * Description: 通过teamId锁定一个team
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
     * Description:获取课程下的所有小组（主从课程）
     *
     * @Author: 17Wang
     * @Time: 11:38 2018/12/23
     */
    public List<Team> listTeamByCourseId(long courseId) throws MyException {
        return teamDao.listByCourseId(courseId, TeamDao.HAS_CCLASS);
    }

    /**
     * Description: 查找我在这门课下的队伍
     * 搞定！ 通过courseid和我的id查找我在哪个班级下面，再通过班级号和学号查找队伍信息
     *
     * @Author: 17Wang
     * @Time: 11:48 2018/12/23
     */
    public Team getTeamByCourseIdAndStudentId(long courseId) throws MyException {
        Student nowStudent = (Student) UserUtils.getNowUser();
        CClass cClass = cClassDao.getCClassByStudentIdAndCourseId(nowStudent.getId(), courseId);

        Team team = teamDao.getTeamBycClassIdAndStudentId(cClass.getId(), nowStudent.getId(),
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
     * Description: 添加成员，修改team_student就可以了
     *
     * @Author: 17Wang
     * @Time: 23:08 2018/12/19
     */
    @Transactional(rollbackFor = Exception.class)
    public boolean addMemberToTeam(long teamId, List<Long> membersIds) throws Exception {
        if (membersIds.isEmpty()) {
            throw new MyException("没有需要添加的成员或者参数名写错了，是studentId哦", MyException.ID_FORMAT_ERROR);
        }
        for (Long memberId : membersIds) {
            ///如果没有该学生
            Student student = studentDao.getStudentById(memberId);
            //获取team信息，主要是为了拿classId
            //因为只有主课程能修改team，所以拿team表里的class没有任何问题
            Team team = teamDao.getTeamById(teamId);
            //如果该学生已有队伍
            Team tempTeam = null;
            try {
                tempTeam = teamDao.getTeamBycClassIdAndStudentId(team.getcClassId(), memberId);
            } catch (Exception e) {}
            if (tempTeam != null) {
                throw new MyException("成员" + student.getStudentName() + "已有队伍", MyException.AUTHORIZATION_ERROR);
            }
            teamDao.addTeamStudentByTeamIdAndStudentId(teamId, memberId);
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
        //自己不能踢出自己
        if (teamId == memberId) {
            throw new MyException("自己不能踢出自己么么", MyException.AUTHORIZATION_ERROR);
        }
        //上述判断通过，将该成员踢出
        teamDao.deleteTeamStudentByTeamIdAndStudentId(teamId, memberId);

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
        List<Student> students = studentDao.listByTeamId(teamId);
        for (Student student : students) {
            removeMemberFromTeam(teamId, student.getId());
        }
        //删除这个小组
        teamDao.deleteTeamById(teamId);
        return true;
    }
}
