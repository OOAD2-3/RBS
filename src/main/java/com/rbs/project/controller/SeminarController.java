package com.rbs.project.controller;

import com.rbs.project.exception.MyException;
import com.rbs.project.pojo.dto.CreateSeminarDTO;
import com.rbs.project.pojo.dto.UpdateSeminarDTO;
import com.rbs.project.pojo.entity.*;
import com.rbs.project.pojo.vo.QuestionInfoVO;
import com.rbs.project.service.*;
import com.rbs.project.utils.JsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: WinstonDeng
 * @Description: OOAD_Course_ManageSystem
 * @Date: Created in 11:43 2018/12/18
 * @Modified by:
 */
@RestController
@RequestMapping("/seminar")
public class SeminarController {
    @Autowired
    private SeminarService seminarService;

    @Autowired
    private CClassSeminarService cClassSeminarService;

    /**
     * Description: 新增讨论课
     * @Author: WinstonDeng
     * @Date: 13:04 2018/12/18
     */
    @PostMapping("")
    @ResponseBody
    public ResponseEntity<Long> createSeminar(@RequestBody CreateSeminarDTO createSeminarDTO) throws Exception {
        //初始化新增讨论课id
        long createSeminarId = -1;
        Seminar seminar=new Seminar();
        if(createSeminarDTO.getCourseId()==null){
            seminar.setCourseId(-1);
        }else {
            seminar.setCourseId(createSeminarDTO.getCourseId());
        }
        if(createSeminarDTO.getRoundId()==null){
            seminar.setRoundId(-1);
        }else{
            seminar.setRoundId(createSeminarDTO.getRoundId());
        }
        seminar.setName(createSeminarDTO.getName());
        seminar.setIntro(createSeminarDTO.getIntro());
        seminar.setMaxTeam(createSeminarDTO.getMaxTeam());
        seminar.setVisible(createSeminarDTO.getVisible());
        seminar.setSerial(createSeminarDTO.getSerial());
        seminar.setEnrollStartTime(JsonUtils.StringToTimestamp(createSeminarDTO.getEnrollStartTime()));
        seminar.setEnrollEndTime(JsonUtils.StringToTimestamp(createSeminarDTO.getEnrollEndTime()));
        //获得主键
        createSeminarId=seminarService.addSemianr(seminar);
        return ResponseEntity.ok().body(createSeminarId);
    }
    
    /**
     * Description: 按id获取讨论课
     * @Author: WinstonDeng
     * @Date: 23:25 2018/12/19
     */
    @GetMapping("/{seminarId}/")
    @ResponseBody
    public ResponseEntity<Map<String,Object>> getSeminarById(@PathVariable("seminarId")long seminarId,@RequestParam("cClassId") long cClassId) throws MyException{
        if((Long)seminarId==null){
            throw new MyException("seminarId不能为空",MyException.ID_FORMAT_ERROR);
        }
        if((Long)cClassId==null){
            throw new MyException("classId不能为空",MyException.ID_FORMAT_ERROR);
        }
        Seminar seminar=seminarService.getSeminarById(seminarId);
        //转换格式
        Map<String,Object> seminarView=new HashMap<>();
        seminarView.put("courseId",seminar.getCourseId());
        seminarView.put("courseName",seminar.getCourse().getName());
        seminarView.put("roundSerial",seminar.getRound().getSerial());
        seminarView.put("seminarTopic",seminar.getName());
        seminarView.put("seminarSerial",seminar.getSerial());
        seminarView.put("seminarIntro",seminar.getIntro());
        seminarView.put("visible",seminar.getVisible());
        //状态
        seminarView.put("status",cClassSeminarService.getCClassSeminar(cClassId,seminarId).getStatus());
        return ResponseEntity.ok().body(seminarView);
    }

    /**
     * Description: 删除讨论课
     * @Author: WinstonDeng
     * @Date: 13:04 2018/12/18
     */
    @DeleteMapping("/{seminarId}")
    @ResponseBody
    public ResponseEntity<Boolean> deleteSeminarById(@PathVariable("seminarId") long seminarId) throws MyException{
        if((Long)seminarId==null){
            throw new MyException("seminarId不能为空",MyException.ID_FORMAT_ERROR);
        }
        return ResponseEntity.ok().body(seminarService.removeSeminarById(seminarId));
    }

    /**
     * Description: 修改讨论课信息
     * @Author: WinstonDeng
     * @Date: 13:06 2018/12/18
     */
    @PutMapping("/{seminarId}")
    @ResponseBody
    public ResponseEntity<Boolean> updateSeminarById(@PathVariable("seminarId") long seminarId, @RequestBody UpdateSeminarDTO updateSeminarDTO) throws Exception {
        if((Long)seminarId==null){
            throw new MyException("seminarId不能为空",MyException.ID_FORMAT_ERROR);
        }
        //DTO转Entity
        Seminar seminar=new Seminar();
        seminar.setId(seminarId);
        seminar.setName(updateSeminarDTO.getIntro());
        seminar.setIntro(updateSeminarDTO.getIntro());
        seminar.setMaxTeam(updateSeminarDTO.getMaxTeam());
        seminar.setVisible(updateSeminarDTO.getVisible());
        seminar.setSerial(updateSeminarDTO.getSerial());
        seminar.setEnrollStartTime(updateSeminarDTO.getEnrollStartTime());
        seminar.setEnrollEndTime(updateSeminarDTO.getEnrollEndTime());
        return ResponseEntity.ok().body(seminarService.updateSeminar(seminar));
    }

    /**
     * Description: 修改班级讨论课 前端就算只修改一个字段，剩余字段都要把查到的原封不动送过来
     * @Author: WinstonDeng
     * @Date: 11:27 2018/12/19
     */
    @PutMapping("/{seminarId}/class/{classId}")
    @ResponseBody
    public ResponseEntity<Boolean> updateCClassSeminar(@PathVariable("seminarId") long  seminarId,
                                                       @PathVariable("classId") long cClassId,
                                                       @RequestBody CClassSeminar cClassSeminar) throws MyException{
        if((Long)seminarId==null){
            throw new MyException("seminarId不能为空",MyException.ID_FORMAT_ERROR);
        }
        if((Long)cClassId==null){
            throw new MyException("classId不能为空",MyException.ID_FORMAT_ERROR);
        }
        cClassSeminar.setcClassId(cClassId);
        cClassSeminar.setSeminarId(seminarId);
        return ResponseEntity.ok().body(cClassSeminarService.updateCClassSeminar(cClassSeminar));
    }

    /**
     * Description: 查看班级下讨论课的所有提问
     * ！！！！应有websocket！！！！！
     *
     * @Author: WinstonDeng
     * @Date: 16:05 2018/12/22
     */
    @GetMapping("/{seminarId}/class/{classId}/question")
    @ResponseBody
    public ResponseEntity<List<QuestionInfoVO>> listAllQuestions(@PathVariable("seminarId")long seminarId, @PathVariable("classId") long cClassId) throws MyException{
        if((Long)seminarId==null){
            throw new MyException("seminarId不能为空",MyException.ID_FORMAT_ERROR);
        }
        if((Long)cClassId==null){
            throw new MyException("classId不能为空",MyException.ID_FORMAT_ERROR);
        }
        List<Question> questions=cClassSeminarService.listAllQuestionsByCClassIdAndSeminarId(cClassId,seminarId);
        List<QuestionInfoVO> questionInfoVOS=new ArrayList<>();
        for(Question question:questions){
            questionInfoVOS.add(new QuestionInfoVO(question));
        }
        return ResponseEntity.ok().body(questionInfoVOS);
    }

    /**
     * Description: 修改班级下讨论课的提问
     *   1.选择提问
     *   2.打分
     *   ！！！！！websocket通知！！！！！！
     * @Author: WinstonDeng
     * @Date: 16:40 2018/12/22
     */
    @PutMapping("/{seminarId}/class/{classId}/question")
    @ResponseBody
    public ResponseEntity<Boolean> updateQuestion(@PathVariable("seminarId")long seminarId,
                                                  @PathVariable("classId") long cClassId,
                                                  @RequestBody QuestionInfoVO questionInfoVO) throws MyException{
        if((Long)seminarId==null){
            throw new MyException("seminarId不能为空",MyException.ID_FORMAT_ERROR);
        }
        if((Long)cClassId==null){
            throw new MyException("classId不能为空",MyException.ID_FORMAT_ERROR);
        }
        Question question=new Question();
        question.setScore(questionInfoVO.getScore());
        if(questionInfoVO.getSelected()==true){
            question.setSelected(1);
        }else if(questionInfoVO.getSelected()==false){
            question.setSelected(0);
        }
        if(questionInfoVO.getQuestionId()==null){
           throw new MyException("questionId不能为空",MyException.ID_FORMAT_ERROR);
        }
        question.setId(questionInfoVO.getQuestionId());
        return ResponseEntity.ok().body(cClassSeminarService.updateQuestion(question));
    }
}
