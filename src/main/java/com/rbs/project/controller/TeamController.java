package com.rbs.project.controller;

import com.rbs.project.exception.MyException;
import com.rbs.project.pojo.entity.Student;
import com.rbs.project.pojo.entity.Team;
import com.rbs.project.pojo.vo.CClassInfoVO;
import com.rbs.project.pojo.vo.CourseInfoVO;
import com.rbs.project.pojo.vo.UserVO;
import com.rbs.project.service.TeamService;
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
            userVOS.add(new UserVO(student));
        }

        map.put("members", userVOS);
        return map;
    }

    /**
     * Description: 修改小组状态，暂时不用
     *
     * @Author: 17Wang
     * @Time: 22:59 2018/12/19
     */
    @PutMapping("/{teamId}")
    @ResponseBody
    public ResponseEntity<Boolean> updateTeam(@PathVariable("teamId") long teamId, @RequestBody Team team) {
        if (team.getStudents() == null) {
            team.setStudents(new ArrayList<>());
        }

        return ResponseEntity.status(404).body(teamService.updateTeam(team));
    }

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

    @DeleteMapping("/{teamId}/member")
    @ResponseBody
    public ResponseEntity<Boolean> removeMemberFromTeam(@PathVariable("teamId") long teamId, @RequestBody Map<String, Long> member) throws Exception {
        Long memberId = member.get("studentId");
        if (memberId == null) {
            throw new MyException("参数传递错误，是studentId", MyException.ID_FORMAT_ERROR);
        }
        return ResponseEntity.ok(teamService.removeMemberFromTeam(teamId,memberId));
    }
}
