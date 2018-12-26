package com.rbs.project.controller;

import com.rbs.project.exception.MyException;
import com.rbs.project.pojo.dto.CreateSeminarDTO;
import com.rbs.project.pojo.dto.UpdateSeminarDTO;
import com.rbs.project.pojo.entity.*;
import com.rbs.project.pojo.vo.QuestionInfoVO;
import com.rbs.project.service.*;
import com.rbs.project.utils.JsonUtils;
import com.rbs.project.utils.UserUtils;
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
     *
     * @Author: WinstonDeng
     * @Date: 13:04 2018/12/18
     */
    @PostMapping
    @ResponseBody
    public ResponseEntity<Long> createSeminar(@RequestBody CreateSeminarDTO createSeminarDTO) throws Exception {
        //初始化新增讨论课id
        long createSeminarId = -1;
        Seminar seminar = new Seminar();
        if (createSeminarDTO.getCourseId() == null) {
            throw new MyException("courseId不能为空",MyException.ID_FORMAT_ERROR);
        }
        seminar.setCourseId(createSeminarDTO.getCourseId());
        if (createSeminarDTO.getRoundId() == null) {
            seminar.setRoundId(-1);
        } else {
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
        createSeminarId = seminarService.addSemianr(seminar);
        return ResponseEntity.ok().body(createSeminarId);
    }

    /**
     * Description: 按id获取讨论课
     *
     * @Author: WinstonDeng
     * @Date: 23:25 2018/12/19
     */
    @GetMapping("/{seminarId}")
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
        seminarView.put("seminarId",seminar.getId());
        seminarView.put("courseId",seminar.getCourseId());
        seminarView.put("courseName",seminar.getCourse().getName());
        seminarView.put("roundSerial",seminar.getRound().getSerial());
        seminarView.put("seminarTopic",seminar.getName());
        seminarView.put("seminarSerial",seminar.getSerial());
        seminarView.put("seminarIntro",seminar.getIntro());
        seminarView.put("visible",seminar.getVisible());
        //状态
        seminarView.put("status", cClassSeminarService.getCClassSeminar(cClassId, seminarId).getStatus());
        return ResponseEntity.ok().body(seminarView);
    }

    /**
     * Description: 删除讨论课
     *
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
     *
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
        Seminar seminar = new Seminar();
        seminar.setId(seminarId);
        if(updateSeminarDTO.getRoundId()==null){
            seminar.setRoundId(-1);
        }else{
        seminar.setRoundId(updateSeminarDTO.getRoundId());
        }
        seminar.setName(updateSeminarDTO.getTopic());
        seminar.setIntro(updateSeminarDTO.getIntro());
        seminar.setMaxTeam(updateSeminarDTO.getMaxTeam());
        seminar.setVisible(updateSeminarDTO.getVisible());
        seminar.setSerial(updateSeminarDTO.getSerial());
        seminar.setEnrollStartTime(JsonUtils.StringToTimestamp(updateSeminarDTO.getEnrollStartTime()));
        seminar.setEnrollEndTime(JsonUtils.StringToTimestamp(updateSeminarDTO.getEnrollEndTime()));
        return ResponseEntity.ok().body(seminarService.updateSeminar(seminar));
    }

    /**
     * Description: 修改班级讨论课 reportDDL
     *
     * @Author: WinstonDeng
     * @Date: 11:27 2018/12/19
     */
    @PutMapping("/{seminarId}/class/{classId}/reportDDL")
    @ResponseBody
    public ResponseEntity<Boolean> updateCClassSeminarReportDDL(@PathVariable("seminarId") long seminarId,
                                                       @PathVariable("classId") long cClassId,
                                                       @RequestBody Map<String,String> updateMap) throws MyException{
        if((Long)seminarId==null){
            throw new MyException("seminarId不能为空",MyException.ID_FORMAT_ERROR);
        }
        if((Long)cClassId==null){
            throw new MyException("classId不能为空",MyException.ID_FORMAT_ERROR);
        }
        final String reportDDL="reportDDL";
        CClassSeminar cClassSeminar=cClassSeminarService.getCClassSeminar(cClassId,seminarId);
        if(updateMap.get(reportDDL).isEmpty()){
           throw new MyException("reportDDL不能为空",MyException.ERROR);
        }
        cClassSeminar.setReportDDL(JsonUtils.StringToTimestamp(updateMap.get(reportDDL)));
        return ResponseEntity.ok().body(cClassSeminarService.updateCClassSeminar(cClassSeminar));
    }

    /**
     * Description: 修改班级讨论课 status
     *
     * @Author: WinstonDeng
     * @Date: 11:27 2018/12/19
     */
    @PutMapping("/{seminarId}/class/{classId}/status")
    @ResponseBody
    public ResponseEntity<Boolean> updateCClassSeminarStatus(@PathVariable("seminarId") long seminarId,
                                                                @PathVariable("classId") long cClassId,
                                                                @RequestBody Map<String,String> updateMap) throws MyException{
        if((Long)seminarId==null){
            throw new MyException("seminarId不能为空",MyException.ID_FORMAT_ERROR);
        }
        if((Long)cClassId==null){
            throw new MyException("classId不能为空",MyException.ID_FORMAT_ERROR);
        }
        final String status="status";
        CClassSeminar cClassSeminar=cClassSeminarService.getCClassSeminar(cClassId,seminarId);
        if(updateMap.get(status).isEmpty()){
            throw new MyException("status不能为空",MyException.ERROR);
        }
        cClassSeminar.setStatus(Integer.parseInt(updateMap.get(status)));
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

    /**
     * Description: 发起提问
     * ！！！！！！此次应有websocket！！！！！！
     *
     * @Author: WinstonDeng
     * @Date: 22:15 2018/12/22
     */
    @PostMapping("/{seminarId}/class/{classId}/question")
    public ResponseEntity<Long> createQuestion(@PathVariable("seminarId")long seminarId,
                                               @PathVariable("classId") long cClassId,
                                               @RequestBody QuestionInfoVO questionInfoVO)throws MyException{
        Question question=new Question();
        if((Long)seminarId==null){
            throw new MyException("seminarId不能为空",MyException.ID_FORMAT_ERROR);
        }
        if((Long)cClassId==null){
            throw new MyException("classId不能为空",MyException.ID_FORMAT_ERROR);
        }
        CClassSeminar cClassSeminar=cClassSeminarService.getCClassSeminar(cClassId,seminarId);
        question.setcClassSeminarId(cClassSeminar.getId());
        if(questionInfoVO.getAttendanceId()==null){
            throw new MyException("attendanceId不能为空",MyException.ID_FORMAT_ERROR);
        }
        question.setAttendanceId(questionInfoVO.getAttendanceId());
        if(questionInfoVO.getTeamId()==null){
            throw new MyException("teamId不能为空",MyException.ID_FORMAT_ERROR);
        }
        question.setTeamId(questionInfoVO.getTeamId());
        if(questionInfoVO.getStudentId()==null){
            throw new MyException("studentId不能为空",MyException.ID_FORMAT_ERROR);
        }
        question.setStudentId(questionInfoVO.getStudentId());
        //默认未被选中
        question.setSelected(0);
        return ResponseEntity.ok().body(cClassSeminarService.addQuestion(question));
    }

    /**
     * Description: 获取正在进行的讨论课
     *
     * @Author: WinstonDeng
     * @Date: 23:10 2018/12/25
     */
    @GetMapping("/underway")
    @ResponseBody
    public ResponseEntity<List<Map<String,Long>>> getUnderWaySeminarId()throws MyException{
        Teacher teacher= (Teacher) UserUtils.getNowUser();
        List<CClassSeminar> cClassSeminars=cClassSeminarService.listAllUnderWaySeminarsByTeacherId(teacher.getId());
        List<Map<String,Long>> maps=new ArrayList<>();
        for(CClassSeminar cClassSeminar:cClassSeminars){
            Map<String,Long> map=new HashMap<>();
            map.put("seminarId",cClassSeminar.getSeminarId());
            map.put("cClassId",cClassSeminar.getcClassId());
            maps.add(map);
        }
        return ResponseEntity.ok().body(maps);
    }
}
