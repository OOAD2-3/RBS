package com.rbs.project.controller;

import com.rbs.project.exception.MyException;
import com.rbs.project.pojo.entity.Student;
import com.rbs.project.pojo.entity.Teacher;
import com.rbs.project.pojo.entity.Team;
import com.rbs.project.pojo.entity.TeamValidApplication;
import com.rbs.project.pojo.vo.CClassInfoVO;
import com.rbs.project.pojo.vo.CourseInfoVO;
import com.rbs.project.pojo.vo.UserVO;
import com.rbs.project.service.ApplicationService;
import com.rbs.project.service.TeamService;
import com.rbs.project.utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Description:
 *
 * @Author: 17Wang
 * @Date: 11:10 2018/12/19
 */
@RestController
@RequestMapping("/team")
public class TeamController {
    @Autowired
    private TeamService teamService;

    @Autowired
    private ApplicationService applicationService;

    /**
     * Description: 创建小组
     *
     * @Author: 17Wang
     * @Time: 13:34 2018/12/23
     */
    @PostMapping
    @ResponseBody
    public ResponseEntity<Boolean> createTeam(@RequestBody Team team) throws Exception {
        Team trueTeam = new Team();
        trueTeam.setName(team.getName());
        trueTeam.setCourseId(team.getCourseId());
        trueTeam.setcClassId(team.getcClassId());
        trueTeam.setLeaderId(team.getLeaderId());
        //只要studentid
        if (team.getStudents() == null) {
            trueTeam.setStudents(new ArrayList<>());
        } else {
            trueTeam.setStudents(team.getStudents());
        }

        return ResponseEntity.ok(teamService.createTeam(trueTeam));
    }

    /**
     * Description: 通过teamId获取一个小组
     *
     * @Author: 17Wang
     * @Time: 13:34 2018/12/23
     */
    @GetMapping("/{teamId}")
    @ResponseBody
    public Map<String, Object> getTeamById(@PathVariable("teamId") long teamId) throws MyException {
        Team team = teamService.getTeamById(teamId);
        Map<String, Object> map = new HashMap<>();
        map.put("id", team.getId());
        map.put("name", team.getName());
        map.put("course", new CourseInfoVO(team.getCourse()));
        map.put("class", new CClassInfoVO(team.getcClass()));
        map.put("leader", new UserVO(team.getLeader()));

        List<UserVO> userVOS = new ArrayList<>();
        for (Student student : team.getStudents()) {
            if (student.getId() != team.getLeader().getId()) {
                userVOS.add(new UserVO(student));
            }
        }

        map.put("members", userVOS);
        return map;
    }

    /**
     * Description: 添加小组成员
     *
     * @Author: 17Wang
     * @Time: 13:34 2018/12/23
     */
    @PutMapping("/{teamId}/members")
    @ResponseBody
    public ResponseEntity<Boolean> addMemberToTeam(@PathVariable("teamId") long teamId, @RequestBody List<Map<String, Long>> members) throws Exception {
        List<Long> membersIds = new ArrayList<>();
        for (Map<String, Long> map : members) {
            if (map.get("studentId") != null) {
                membersIds.add(map.get("studentId"));
            }
        }
        return ResponseEntity.ok(teamService.addMemberToTeam(teamId, membersIds));
    }

    /**
     * Description: 删除小组成员
     *
     * @Author: 17Wang
     * @Time: 13:34 2018/12/23
     */
    @DeleteMapping("/{teamId}/member")
    @ResponseBody
    public ResponseEntity<Boolean> removeMemberFromTeam(@PathVariable("teamId") long teamId, @RequestBody Map<String, Long> member) throws Exception {
        Long memberId = member.get("studentId");
        if (memberId == null) {
            throw new MyException("参数传递错误，是studentId", MyException.ID_FORMAT_ERROR);
        }
        return ResponseEntity.ok(teamService.removeMemberFromTeam(teamId, memberId));
    }

    /**
     * Description:解散小组
     *
     * @Author: 17Wang
     * @Time: 13:35 2018/12/23
     */
    @DeleteMapping("/{teamId}/allmember")
    @ResponseBody
    public ResponseEntity<Boolean> dissolveTeam(@PathVariable("teamId") long teamId) throws Exception {
        return ResponseEntity.ok(teamService.dissolveTeam(teamId));
    }

    /**
     * Description: 新增team合法化请求
     *
     * @Author: 17Wang
     * @Time: 13:37 2018/12/23
     */
    @PostMapping("/{teamId}/request")
    @ResponseBody
    public ResponseEntity<Boolean> addTeamValidRequest(@PathVariable("teamId") long teamId, @RequestBody Map<String, String> map) throws Exception {
        String reason = map.get("reason");
        return ResponseEntity.ok(applicationService.addTeamValidApplication(teamId,reason));
    }
    
    /**
     * Description: 查看team的请求
     * @Author: 17Wang
     * @Time: 14:12 2018/12/23
    */
    @GetMapping("/{teamId}/request")
    @ResponseBody
    public Map<String,Object> getTeamValidRequest(@PathVariable("teamId") long teamId) throws MyException {
        TeamValidApplication teamValidApplication=applicationService.getTeamValidRequestByTeamId(teamId);
        Map<String,Object> map=new HashMap<>();
        map.put("applicationId", teamValidApplication.getId());
        map.put("reason", teamValidApplication.getReason());
        map.put("status", teamValidApplication.getStatus());

        return map;
    }
}
