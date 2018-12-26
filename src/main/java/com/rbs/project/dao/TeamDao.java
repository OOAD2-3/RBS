package com.rbs.project.dao;

import com.rbs.project.exception.MyException;
import com.rbs.project.mapper.*;
import com.rbs.project.mapper.strategy.*;
import com.rbs.project.pojo.entity.Student;
import com.rbs.project.pojo.entity.Team;
import com.rbs.project.pojo.strategy.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Description:
 *
 * @Author: 17Wang
 * @Date: 11:13 2018/12/19
 */
@Repository
public class TeamDao {
    @Autowired
    private TeamMapper teamMapper;

    @Autowired
    private CourseMapper courseMapper;

    @Autowired
    private CClassMapper cClassMapper;

    @Autowired
    private StudentMapper studentMapper;

    @Autowired
    private TeamStudentMapper teamStudentMapper;

    @Autowired
    private CClassTeamMapper cClassTeamMapper;

    @Autowired
    private CClassStudentMapper cClassStudentMapper;

    //策略

    @Autowired
    private TeamStrategyMapper teamStrategyMapper;

    @Autowired
    private TeamOrStrategyMapper teamOrStrategyMapper;

    @Autowired
    private TeamAndStrategyMapper teamAndStrategyMapper;

    @Autowired
    private MemberLimitStrategyMapper memberLimitStrategyMapper;

    @Autowired
    private CourseMemberLimitStrategyMapper courseMemberLimitStrategyMapper;

    @Autowired
    private ConflictCourseStrategyMapper conflictCourseStrategyMapper;

    public static final int HAS_COURSE = 0;
    public static final int HAS_CCLASS = 1;
    public static final int HAS_LEADER = 2;
    public static final int HAS_MEMBERS = 3;

    private void hasSomethingFun(Team team, int... hasSomething) {
        for (int i : hasSomething) {
            if (i == HAS_COURSE) {
                team.setCourse(courseMapper.findById(team.getCourseId()));
            }
            if (i == HAS_CCLASS) {
                team.setcClass(cClassMapper.findById(team.getcClassId()));
            }
            if (i == HAS_LEADER) {
                team.setLeader(studentMapper.findById(team.getLeaderId()));
            }
            if (i == HAS_MEMBERS) {
                team.setStudents(studentMapper.findByTeamId(team.getId()));
            }
        }
    }

    /**
     * Description: 通过teamid返回一个team
     *
     * @Author: 17Wang
     * @Time: 22:22 2018/12/19
     */
    public Team getTeamById(long teamId, int... hasSomething) throws MyException {
        Team team = teamMapper.findById(teamId);
        if (team == null) {
            throw new MyException("获取队伍错误！找不到该队伍", MyException.NOT_FOUND_ERROR);
        }
        hasSomethingFun(team, hasSomething);
        return team;
    }

    /**
     * Description: 通过leaderId返回一个team
     *
     * @Author: 17Wang
     * @Time: 21:02 2018/12/23
     */
    public Team getTeamByLeaderId(long leaderId, int... hasSomething) throws MyException {
        Team team = teamMapper.findByLeaderId(leaderId);
        hasSomethingFun(team, hasSomething);
        return team;
    }

    /**
     * Description: 新建队伍 在这个方法同时新增klass_team
     *
     * @Author: 17Wang
     * @Time: 9:06 2018/12/20
     */
    @Transactional(rollbackFor = Exception.class)
    public boolean addTeam(Team team) throws Exception {
        if (!teamMapper.insertTeam(team)) {
            throw new MyException("新建队伍出错！Team数据库处理错误", MyException.ERROR);
        }
        if (!cClassTeamMapper.insertBycClassIdAndTeamId(team.getcClassId(), team.getId())) {
            throw new MyException("新建队伍出错！KlassTeam数据库处理错误", MyException.ERROR);
        }
        return true;
    }

    /**
     * Description:
     *
     * @Author: 17
     * @Date: 15:48 2018/12/23
     */
    public Team getTeamBycClassIdAndStudentId(long cClassId, long studentId, int... hasSomething) throws MyException {
        Team team = teamMapper.getTeamBycClassIdAndStudentId(cClassId, studentId);
        if (team == null) {
            throw new MyException("该学生在该班级下无小组", MyException.NOT_FOUND_ERROR);
        }
        hasSomethingFun(team, hasSomething);
        return team;
    }

    /**
     * Description: 获取一个班级下的所有队伍
     *
     * @Author: 17Wang
     * @Time: 9:39 2018/12/20
     */
    public List<Team> listByCClassId(long cClassId, int... hasSomething) {
        List<Team> teams = teamMapper.findByCClassId(cClassId);
        for (Team team : teams) {
            hasSomethingFun(team, hasSomething);
        }
        return teams;
    }

    /**
     * Description: 通过课程id查看队伍列表（主从都可以）
     *
     * @Author: WinstonDeng
     * @Date: 21:24 2018/12/22
     */
    public List<Team> listByCourseId(long courseId, int... hasSomething) throws MyException {
        List<Team> teams = teamMapper.findByCourseId(courseId);
        if (teams == null) {
            throw new MyException("查看队伍错误！该记录不存在", MyException.NOT_FOUND_ERROR);
        }
        for (Team team : teams) {
            hasSomethingFun(team, hasSomething);
        }
        return teams;
    }

    /**
     * Description:
     * （已做）删除小组 删team表 删klass_team表 删team_student
     * （添加）成绩：
     *
     * @Author: 17Wang
     * @Time: 10:59 2018/12/23
     */
    public boolean deleteTeamById(long teamId) throws MyException {
        //将该小组下的成员置为无小组状态
        //通过删除team_student表，解除team和student的关系
        List<Student> students = studentMapper.findByTeamId(teamId);
        for (Student student : students) {
            deleteTeamStudentByTeamIdAndStudentId(teamId, student.getId());
        }
        //检查是否有该行
        Team team = getTeamById(teamId);
        if (!teamMapper.deleteById(teamId)) {
            throw new MyException("删除小组错误！数据库处理错误", MyException.ERROR);
        }
        //直接删掉所有关于team的东西
        if (!cClassTeamMapper.deleteByTeamId(teamId)) {
            throw new MyException("删除小组错误！klass_team处理错误", MyException.ERROR);
        }
        return true;
    }

    /**
     * Description: 修改小组状态
     *
     * @Author: 17Wang
     * @Time: 15:20 2018/12/23
     */
    public boolean updateStatusByTeamId(int status, long teamId) throws Exception {
        getTeamById(teamId);
        if (!teamMapper.updateStatusById(status, teamId)) {
            throw new MyException("修改小组状态错误！数据库处理错误", MyException.ERROR);
        }
        return true;
    }

    /**
     * Description: 新增teamStudent表字段
     *
     * @Author: 17Wang
     * @Time: 22:52 2018/12/25
     */
    public boolean addTeamStudentByTeamIdAndStudentId(long teamId, long studentId) throws Exception {
        if (!teamStudentMapper.insertByTeamIdAndStudentId(teamId, studentId)) {
            throw new MyException("新增teamStudent表字段错误！数据库处理错误", MyException.ERROR);
        }
        return true;
    }

    /**
     * Description: 删除teamStudent表字段
     *
     * @Author: 17Wang
     * @Time: 23:43 2018/12/25
     */
    public boolean deleteTeamStudentByTeamIdAndStudentId(long teamId, long studentId) throws MyException {
        if (!teamStudentMapper.deleteByTeamIdAndStudentId(teamId, studentId)) {
            throw new MyException("新增teamStudent表字段错误！数据库处理错误", MyException.ERROR);
        }
        return true;
    }

    /**
     * Description: 新增klass_team表
     *
     * @Author: WinstonDeng
     * @Date: 16:34 2018/12/26
     */
    public boolean addCClassTeam(long teamId, long cClassId) throws Exception {
        if (!cClassTeamMapper.insertBycClassIdAndTeamId(cClassId, teamId)) {
            throw new MyException("新增klass_team关系错误！数据库处理错误", MyException.ERROR);
        }
        return true;
    }

    //=============================================究极策略递归============================================//

    public boolean judgeTeam(String strategyName, long strategyId) {
        boolean flag = false;
        switch (strategyName) {
            case "TeamAndStrategy":
                flag = TeamAndStrategy(strategyId);
                break;
            case "TeamOrStrategy":
                flag = TeamOrStrategy(strategyId);
                break;
            case "ConflictCourseStrategy":
                flag = ConflictCourseStrategy(strategyId);
                break;
            case "MemberLimitStrategy":
                flag = MemberLimitStrategy(strategyId);
                break;
            case "CourseMemberLimitStrategy":
                flag = CourseMemberLimitStrategy(strategyId);
                break;
            default:
                ;
        }
        return flag;
    }

    /**
     * Description:team策略
     *
     * @Author: 17Wang
     * @Time: 0:44 2018/12/27
     */
    private boolean teamStrategy(long teamId) {
        //获取当前team的信息
        Team team = teamMapper.findById(teamId);
        //获取所有的策略
        List<TeamStrategy> teamStrategies = teamStrategyMapper.findByCourseId(team.getCourseId());
        for (TeamStrategy teamStrategy : teamStrategies) {
            //如果一个为false，直接返回false
            if (!judgeTeam(teamStrategy.getStrategyName(), teamStrategy.getStrategyId())) {
                return false;
            }
        }
        return true;
    }

    /**
     * Description:TeamAndStrategy
     *
     * @Author: 17Wang
     * @Time: 0:44 2018/12/27
     */
    private boolean TeamAndStrategy(long strategyId) {
        // 和 策略
        List<TeamAndStrategy> teamAndStrategies = teamAndStrategyMapper.findById(strategyId);
        for (TeamAndStrategy teamAndStrategy : teamAndStrategies) {
            //如果一个为false，直接返回false
            if (!judgeTeam(teamAndStrategy.getStrategyName(), teamAndStrategy.getStrategyId())) {
                return false;
            }
        }

        return true;
    }

    /**
     * Description:TeamOrStrategy
     *
     * @Author: 17Wang
     * @Time: 0:44 2018/12/27
     */
    private boolean TeamOrStrategy(long strategyId) {
        // 或 策略
        List<TeamOrStrategy> teamOrStrategies = teamOrStrategyMapper.findById(strategyId);
        for (TeamOrStrategy teamOrStrategy : teamOrStrategies) {
            //如果一个为false，直接返回false
            if (!judgeTeam(teamOrStrategy.getStrategyName(), teamOrStrategy.getStrategyId())) {
                return false;
            }
        }

        return true;
    }

    /**
     * Description:ConflictCourseStrategy
     *
     * @Author: 17Wang
     * @Time: 0:44 2018/12/27
     */
    private boolean ConflictCourseStrategy(long strategyId) {
        //冲突课程策略
        List<ConflictCourseStrategy> conflictCourseStrategies=conflictCourseStrategyMapper.findById(strategyId);
        for(ConflictCourseStrategy conflictCourseStrategy:conflictCourseStrategies){
            //TODO 判断冲突课程的策略
        }

        return true;
    }

    /**
     * Description:MemberLimitStrategy
     *
     * @Author: 17Wang
     * @Time: 0:44 2018/12/27
     */
    private boolean MemberLimitStrategy(long strategyId) {
        return true;
    }

    /**
     * Description:CourseMemberLimitStrategy
     *
     * @Author: 17Wang
     * @Time: 0:44 2018/12/27
     */
    private boolean CourseMemberLimitStrategy(long strategyId) {
        return true;
    }

}
