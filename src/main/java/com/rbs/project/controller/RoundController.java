package com.rbs.project.controller;

import com.rbs.project.exception.MyException;
import com.rbs.project.pojo.dto.UpdateRoundDTO;
import com.rbs.project.pojo.entity.CClassSeminar;
import com.rbs.project.pojo.entity.Round;
import com.rbs.project.pojo.relationship.CClassRound;
import com.rbs.project.service.RoundService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @Author: WinstonDeng
 * @Description: OOAD_Course_ManageSystem
 * @Date: Created in 23:55 2018/12/25
 * @Modified by:
 */
@RestController
@RequestMapping("/round")
public class RoundController {
    @Autowired
    private RoundService roundService;

    /**
     * Description: 修改轮次设置 成绩计算方法(只能是 0 平均分 或者 1 最高分)
     * @Author: WinstonDeng
     * @Date: 11:10 2018/12/26
     */
    @PutMapping("/{roundId}")
    @ResponseBody
    public ResponseEntity<Boolean> updateRoundInfo(@PathVariable("roundId")long roundId,@RequestBody UpdateRoundDTO updateRoundDTO) throws MyException{

        if((Long) roundId==null){
            throw new MyException("roundId不能为空",MyException.ID_FORMAT_ERROR);
        }
        if(updateRoundDTO.getPresentationScoreMethod()==null){
            throw new MyException("presentationScoreMethod不能为空",MyException.ERROR);
        }
        if(updateRoundDTO.getReportScoreMethod()==null){
            throw new MyException("reportScoreMethod不能为空",MyException.ERROR);
        }
        if(updateRoundDTO.getQuestionScoreMethod()==null){
            throw new MyException("questionScoreMethod不能为空",MyException.ERROR);
        }
        Round round=new Round();
        round.setId(roundId);
        round.setPresentationScoreMethod(updateRoundDTO.getPresentationScoreMethod());
        round.setReportScoreMethod(updateRoundDTO.getReportScoreMethod());
        round.setQuestionScoreMethod(updateRoundDTO.getQuestionScoreMethod());
        return ResponseEntity.ok().body(roundService.updateScoreMethod(round));
    }

    /**
     * Description: 修改轮次设置中 班级报名次数
     * @Author: WinstonDeng
     * @Date: 11:11 2018/12/26
     */
    @PutMapping("/{roundId}/class/{classId}")
    @ResponseBody
    public ResponseEntity<Boolean> updateCClassRound(@PathVariable("roundId")long roundId,
                                                     @PathVariable("classId") long cClassId,
                                                     @RequestBody Map<String,String> map) throws MyException{
        CClassRound cClassRound=new CClassRound();
        if((Long) roundId==null){
            throw new MyException("roundId不能为空",MyException.ID_FORMAT_ERROR);
        }
        if((Long)cClassId==null){
            throw new MyException("classId不能为空",MyException.ID_FORMAT_ERROR);
        }
        String enrollNumber="enrollNumber";
        if(map.get(enrollNumber)==null){
            throw new MyException("enrollNumber不能为空",MyException.ERROR);
        }
        cClassRound.setRoundId(roundId);
        cClassRound.setcClassId(cClassId);
        cClassRound.setEnrollNumber(Integer.parseInt(map.get(enrollNumber)));
        return ResponseEntity.ok().body(roundService.updateEnrollNumber(cClassRound));
    }
}
