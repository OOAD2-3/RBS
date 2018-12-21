package com.rbs.project.controller;

import com.rbs.project.exception.MyException;
import com.rbs.project.pojo.dto.CreateSeminarDTO;
import com.rbs.project.pojo.dto.UpdateSeminarDTO;
import com.rbs.project.pojo.entity.CClassSeminar;
import com.rbs.project.pojo.entity.Seminar;
import com.rbs.project.service.CClassSeminarService;
import com.rbs.project.service.SeminarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<Long> createSeminar(@RequestBody CreateSeminarDTO createSeminarDTO) throws MyException {
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
        seminar.setEnrollStartTime(createSeminarDTO.getEnrollStartTime());
        seminar.setEnrollEndTime(createSeminarDTO.getEnrollEndTime());
        //获得主键
        createSeminarId=seminarService.addSemianr(seminar);
        return ResponseEntity.ok().body(createSeminarId);
    }
    
    /**
     * Description: 按id获取讨论课
     * @Author: WinstonDeng
     * @Date: 23:25 2018/12/19
     */
    @GetMapping("/{seminarId}")
    @ResponseBody
    public ResponseEntity<Seminar> getSeminarById(@PathVariable("seminarId")long seminarId) throws MyException{
        return ResponseEntity.ok().body(seminarService.getSeminarById(seminarId));
    }

    /**
     * Description: 删除讨论课
     * @Author: WinstonDeng
     * @Date: 13:04 2018/12/18
     */
    @DeleteMapping("/{seminarId}")
    @ResponseBody
    public ResponseEntity<Boolean> deleteSeminarById(@PathVariable("seminarId") long seminarId) throws MyException{
        return ResponseEntity.ok().body(seminarService.removeSeminarById(seminarId));
    }

    /**
     * Description: 修改讨论课信息
     * @Author: WinstonDeng
     * @Date: 13:06 2018/12/18
     */
    @PutMapping("/{seminarId}")
    @ResponseBody
    public ResponseEntity<Boolean> updateSeminarById(@PathVariable("seminarId") long seminarId, @RequestBody UpdateSeminarDTO updateSeminarDTO) throws MyException{
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
        cClassSeminar.setcClassId(cClassId);
        cClassSeminar.setSeminarId(seminarId);
        return ResponseEntity.ok().body(cClassSeminarService.updateCClassSeminar(cClassSeminar));
    }
}
