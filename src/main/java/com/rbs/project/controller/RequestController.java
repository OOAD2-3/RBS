package com.rbs.project.controller;

import com.rbs.project.dao.TeamDao;
import com.rbs.project.exception.MyException;
import com.rbs.project.pojo.entity.Course;
import com.rbs.project.pojo.entity.Teacher;
import com.rbs.project.pojo.entity.Team;
import com.rbs.project.pojo.entity.TeamValidApplication;
import com.rbs.project.pojo.vo.TeamValidApplicationVO;
import com.rbs.project.service.ApplicationService;
import com.rbs.project.service.CourseService;
import com.rbs.project.service.TeamService;
import com.rbs.project.utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Description:
 *
 * @Author: 17Wang
 * @Date: 14:21 2018/12/23
 */
@RestController
@RequestMapping("/request")
public class RequestController {
    @Autowired
    private ApplicationService applicationService;

    @Autowired
    private CourseService courseService;

    @Autowired
    private TeamService teamService;

    /**
     * Description: 老师的专属接口，获取所有的小组合法化请求
     *
     * @Author: 17Wang
     * @Time: 14:40 2018/12/23
     */
    @GetMapping("/team")
    @ResponseBody
    public List<TeamValidApplicationVO> listTeamRequestByTeacherId() throws MyException {
        Teacher teacher = (Teacher) UserUtils.getNowUser();
        List<TeamValidApplication> teamValidApplications = applicationService.listTeamApplicationByTeacherId(teacher.getId());
        List<TeamValidApplicationVO> teamValidApplicationVOS = new ArrayList<>();
        for (TeamValidApplication teamValidApplication : teamValidApplications) {
            Team team = teamService.getTeamById(teamValidApplication.getTeamId(),TeamDao.HAS_CCLASS);
            Course course = courseService.getCourseById(team.getCourseId(),-1);
            teamValidApplicationVOS.add(
                    new TeamValidApplicationVO(teamValidApplication)
                    .setTeam(team)
                    .setCourse(course));
        }
        return teamValidApplicationVOS;
    }

    @PutMapping("/team")
    @ResponseBody
    public ResponseEntity<Boolean> updateTeamApplicationStatus(@RequestParam("requestId") long requestId, @RequestParam("status") int status) throws Exception {
        return ResponseEntity.ok(applicationService.updateTeamValidApplicationStatus(requestId, status));
    }
}
