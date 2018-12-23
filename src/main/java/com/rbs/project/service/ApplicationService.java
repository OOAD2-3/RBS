package com.rbs.project.service;

import com.rbs.project.dao.CourseDao;
import com.rbs.project.dao.TeamApplicationDao;
import com.rbs.project.dao.TeamDao;
import com.rbs.project.exception.MyException;
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
}
