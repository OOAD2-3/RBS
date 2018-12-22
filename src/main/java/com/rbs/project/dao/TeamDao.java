package com.rbs.project.dao;

import com.rbs.project.exception.MyException;
import com.rbs.project.mapper.CClassMapper;
import com.rbs.project.mapper.CourseMapper;
import com.rbs.project.mapper.StudentMapper;
import com.rbs.project.mapper.TeamMapper;
import com.rbs.project.pojo.entity.CClass;
import com.rbs.project.pojo.entity.Student;
import com.rbs.project.pojo.entity.Team;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Collection;
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
                team.setcClass(cClassMapper.findById(team.getCourseId()));
            }
            if (i == HAS_LEADER) {
                team.setLeader(studentMapper.findById(team.getLeaderId()));
            }
            if (i == HAS_MEMBERS) {
                team.setStudents(studentMapper.listByTeamId(team.getId()));
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
     * Description: 新建队伍
     * @Author: 17Wang
     * @Time: 9:06 2018/12/20
    */
    public boolean addTeam(Team team) throws Exception {
        if (!teamMapper.insertTeam(team)) {
            throw new MyException("新建队伍出错！数据库处理错误", MyException.ERROR);
        }

        return true;
    }

    /**
     * Description: 获取一个班级下的所有队伍
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
     * Description: 通过课程id查看队伍列表
     * @Author: WinstonDeng
     * @Date: 21:24 2018/12/22
     */
    public List<Team> listByCourseId(long courseId) throws MyException{
        if(courseMapper.findById(courseId)==null){
            throw new MyException("查看队伍错误！课程不存在",MyException.NOT_FOUND_ERROR);
        }
        List<Team> teams=teamMapper.findByCourseId(courseId);
        if(teams==null){
            throw new MyException("查看队伍错误！该记录不存在",MyException.NOT_FOUND_ERROR);
        }
        return teams;
    }
}
