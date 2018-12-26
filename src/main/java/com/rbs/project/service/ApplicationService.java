package com.rbs.project.service;

import com.rbs.project.dao.*;
import com.rbs.project.exception.MyException;
import com.rbs.project.pojo.entity.ShareTeamApplication;
import com.rbs.project.pojo.entity.Team;
import com.rbs.project.pojo.entity.TeamValidApplication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Description:
 *
 * @Author: 17Wang
 * @Date: 13:43 2018/12/23
 */
@Service
public class ApplicationService {
    @Autowired
    private TeamApplicationDao teamApplicationDao;

    @Autowired
    private TeamDao teamDao;

    @Autowired
    private CourseDao courseDao;

    @Autowired
    private ShareDao shareDao;

    @Autowired
    private CClassDao cClassDao;

    @Autowired
    private RoundScoreDao roundScoreDao;

    @Autowired
    private SeminarScoreDao seminarScoreDao;

    /**
     * Description: 查看team的请求
     * 1、需要加上主从课程！
     *
     * @Author: 17Wang
     * @Time: 14:15 2018/12/23
     */
    public TeamValidApplication getTeamValidRequestByTeamId(long teamId) throws MyException {
        Team team = teamDao.getTeamById(teamId);
        long teacherId = courseDao.getCourseById(team.getCourseId()).getTeacherId();
        return teamApplicationDao.getTeamValidRequestByTeamIdAndTeacherId(teamId, teacherId);
    }

    /**
     * Description: 查看一个老师的所有team的请求
     *
     * @Author: 17Wang
     * @Time: 15:53 2018/12/23
     */
    public List<TeamValidApplication> listTeamApplicationByTeacherId(long teacherId) {
        return teamApplicationDao.getTeamValidRequestByTeacherId(teacherId, TeamApplicationDao.HAS_TEAM);
    }

    /**
     * Description: 新增一条请求
     *
     * @Author: 17Wang
     * @Time: 14:04 2018/12/23
     */
    @Transactional(rollbackFor = Exception.class)
    public boolean addTeamValidApplication(long teamId, String reason) throws Exception {

        TeamValidApplication teamValidApplication = new TeamValidApplication();
        Team team = teamDao.getTeamById(teamId);

        //设置信息
        teamValidApplication.setTeamId(teamId);
        teamValidApplication.setTeacherId(courseDao.getCourseById(team.getCourseId()).getTeacherId());
        teamValidApplication.setReason(reason);
        teamApplicationDao.addTeamValidApplication(teamValidApplication);

        //小组状态
        System.out.println(teamValidApplication.getId());
        if (team.getStatus() != Team.STATUS_IN_REVIEW) {
            teamDao.updateStatusByTeamId(Team.STATUS_IN_REVIEW,
                    teamApplicationDao.getTeamValidRequestById(teamValidApplication.getId()).getTeamId());
        }

        return true;
    }

    /**
     * Description: 修改请求的状态
     *
     * @Author: 17Wang
     * @Time: 14:50 2018/12/23
     */
    @Transactional(rollbackFor = Exception.class)
    public boolean updateTeamValidApplicationStatus(long requestId, int status) throws Exception {
        //如果同意，小组状态改变
        if (status == TeamValidApplication.STATUS_AGREE) {
            teamDao.updateStatusByTeamId(Team.STATUS_OK,
                    teamApplicationDao.getTeamValidRequestById(requestId).getTeamId());
        }
        //如果拒绝，小组状态改变
        if (status == TeamValidApplication.STATUS_DISAGREE) {
            teamDao.updateStatusByTeamId(Team.STATUS_ERROR,
                    teamApplicationDao.getTeamValidRequestById(requestId).getTeamId());
        }
        return teamApplicationDao.updateTeamValidApplicationStatusById(requestId, status);
    }

    /**
     * Description: 修改组队共享请求的状态
     * @Author: WinstonDeng
     * @Date: 20:59 2018/12/24
     */
    @Transactional(rollbackFor = Exception.class)
    public boolean updateTeamShareApplicationStatus(long requestId, Integer status) throws Exception{
        //如果同意
        if(status== ShareTeamApplication.STATUS_ACCEPT){
            //  若请求通过，发出申请的课程为主课程，接受申请的课程为从课程，主课程小组名单映射到从课程中
            //  例如，某小组主课程 A 有5 人，五人中选修从课程 B 的为其中的 3 人，则 B 中此小组为此 3 人组成的小组
            //  若接受共享分组请求，该课程原有分组将被删除，并且，失去发起共享分组、接受其他共享分组请求以及课程中组队的功能
            //  1.删除从课程原有分组
            ShareTeamApplication shareTeamApplication=shareDao.getShareTeamApplicationById(requestId);
            List<Team> teams=teamDao.listByCourseId(shareTeamApplication.getSubCourseId());
            for(Team team:teams){
                //删除分组,调用dao层级联删除函数
                teamDao.deleteTeamById(team.getId());
            }
            //  2.建立主从课程映射
            //  从课程使用队伍学生时，要先确认自己是从课程
            //  从课程学生只看klass_student表的course_id klass_id，不看主课程里这两个字段
            //  3.从课程小组调整
            //  要确认从课程队伍属于哪个班，要先查klass_student表里courseid和teamid对应学生的klass_id，再通过分班策略
            //  进行区分，建议做成工具类方法
        }
        //如果拒绝，只更新请求表的字段
        return shareDao.updateShareTeamApplicationStatus(requestId,status);
    }
}
