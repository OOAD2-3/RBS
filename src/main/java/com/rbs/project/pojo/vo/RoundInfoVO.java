package com.rbs.project.pojo.vo;

import com.rbs.project.pojo.entity.Round;
import com.rbs.project.pojo.entity.Seminar;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: WinstonDeng
 * @Description: OOAD_Course_ManageSystem
 * @Date: Created in 22:33 2018/12/21
 * @Modified by:
 */
public class RoundInfoVO {
    private Long roundId;
    private String roundName;
    private List<SeminarInfoVO> seminarInfoVOS;
    public RoundInfoVO(Round round){
        roundId=round.getId();
        roundName="第"+round.getSerial()+"轮";
        seminarInfoVOS=new ArrayList<>();
        for(Seminar seminar:round.getSeminars()){
            seminarInfoVOS.add(new SeminarInfoVO(seminar));
        }
    }

    public Long getRoundId() {
        return roundId;
    }

    public void setRoundId(Long roundId) {
        this.roundId = roundId;
    }

    public String getRoundName() {
        return roundName;
    }

    public void setRoundName(String roundName) {
        this.roundName = roundName;
    }

    public List<SeminarInfoVO> getSeminarInfoVOS() {
        return seminarInfoVOS;
    }

    public void setSeminarInfoVOS(List<SeminarInfoVO> seminarInfoVOS) {
        this.seminarInfoVOS = seminarInfoVOS;
    }
}
