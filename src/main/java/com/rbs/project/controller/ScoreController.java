package com.rbs.project.controller;

import com.rbs.project.exception.MyException;
import com.rbs.project.pojo.entity.RoundScore;
import com.rbs.project.pojo.entity.Seminar;
import com.rbs.project.pojo.entity.SeminarScore;
import com.rbs.project.pojo.vo.ScoreVO;
import com.rbs.project.service.ScoreService;
import com.rbs.project.service.SeminarService;
import org.springframework.beans.factory.annotation.Autowired;
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

    @GetMapping("/seminarscore")
    @ResponseBody
    public Map<String,Object> listAllSeminarScore(@RequestParam("seminarId") long seminarId, @RequestParam("classId") long classId) throws MyException {
        List<SeminarScore> seminarScores = scoreService.listAllSeminarScoreBySeminarIdAndCClassId(seminarId, classId);
        String seminarName=seminarService.getSeminarById(seminarId).getName();
        Map<String,Object> map=new HashMap<>();
        map.put("seminarName", seminarName);
        List<ScoreVO> scoreVOS=new ArrayList<>();
        for(SeminarScore seminarScore:seminarScores){
            scoreVOS.add(new ScoreVO().revertSeminarScore(seminarScore));
        }
        map.put("scores", scoreVOS);
        return map;
    }

    @GetMapping("/roundscore")
    @ResponseBody
    public Map<String,Object> listAllRoundScore(@RequestParam("roundId") long roundId) throws MyException {
        List<RoundScore> roundScores=scoreService.listAllRoundScoreByRoundId(roundId);
        String roundSerial=
    }
}
