package com.rbs.project.controller;

import com.rbs.project.exception.MyException;
import com.rbs.project.pojo.entity.RoundScore;
import com.rbs.project.pojo.entity.SeminarScore;
import com.rbs.project.pojo.vo.ScoreVO;
import com.rbs.project.service.ScoreService;
import com.rbs.project.service.SeminarService;
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
 * @Date: 16:06 2018/12/22
 */
@RestController
@RequestMapping
public class ScoreController {
    @Autowired
    private ScoreService scoreService;

    @Autowired
    private SeminarService seminarService;

    /**
     * Description: 获取一个班级的一节讨论课所有报名小组的成绩
     *
     * @Author: 17Wang
     * @Time: 20:28 2018/12/22
     */
    @GetMapping("/seminarscore")
    @ResponseBody
    public Map<String, Object> listAllSeminarScore(@RequestParam("seminarId") long seminarId, @RequestParam("classId") long classId) throws MyException {
        List<SeminarScore> seminarScores = scoreService.listAllSeminarScoreBySeminarIdAndCClassId(seminarId, classId);
        String seminarName = seminarService.getSeminarById(seminarId).getName();
        Map<String, Object> map = new HashMap<>();
        map.put("seminarName", seminarName);
        List<ScoreVO> scoreVOS = new ArrayList<>();
        for (SeminarScore seminarScore : seminarScores) {
            scoreVOS.add(new ScoreVO().revertSeminarScore(seminarScore));
        }
        map.put("scores", scoreVOS);
        return map;
    }

    /**
     * Description: （展示）打分或者修改分数
     *
     * @Author: 17Wang
     * @Time: 20:29 2018/12/22
     */
    @PutMapping("/seminarscore/presentationscore")
    @ResponseBody
    public ResponseEntity<Boolean> updatePresentationScore(@RequestBody Map<String, Object> map) throws Exception {
        Long seminarId = Long.valueOf(String.valueOf(map.get("seminarId")));
        Long classId = Long.valueOf(String.valueOf(map.get("classId")));
        Long teamId = Long.valueOf(String.valueOf(map.get("teamId")));
        Double presentationScore = Double.valueOf(String.valueOf(map.get("presentationScore")));
        if (seminarId == null || classId == null || teamId == null || presentationScore == null) {
            throw new MyException("存在为空的字段，或者参数名错误", MyException.ERROR);
        }
        return ResponseEntity.ok(scoreService.updatePresentationScore(seminarId, classId, teamId, presentationScore));
    }

    /**
     * Description: （报告）打分或者修改分数
     *
     * @Author: 17Wang
     * @Time: 20:29 2018/12/22
     */
    @PutMapping("/seminarscore/reportscore")
    @ResponseBody
    public ResponseEntity<Boolean> updateReportScore(@RequestBody Map<String, Number> map) throws Exception {
        Long seminarId = Long.valueOf(String.valueOf(map.get("seminarId")));
        Long classId = Long.valueOf(String.valueOf(map.get("classId")));
        Long teamId = Long.valueOf(String.valueOf(map.get("teamId")));
        Double reportScore = Double.valueOf(String.valueOf(map.get("reportScore")));
        if (seminarId == null || classId == null || teamId == null || reportScore == null) {
            throw new MyException("存在为空的字段，或者参数名错误", MyException.ERROR);
        }
        return ResponseEntity.ok(scoreService.updateReportScore(seminarId, classId, teamId, reportScore));
    }

    /**
     * Description: 获取一个轮次的所有小组的成绩
     *
     * @Author: 17Wang
     * @Time: 21:31 2018/12/22
     */
    @GetMapping("/roundscore")
    @ResponseBody
    public Map<String, Object> listAllRoundScore(@RequestParam("roundId") long roundId) throws MyException {
        List<RoundScore> roundScores = scoreService.listAllRoundScoreByRoundId(roundId);
        return null;
    }
}
