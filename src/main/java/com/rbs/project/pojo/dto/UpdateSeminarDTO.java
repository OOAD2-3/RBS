package com.rbs.project.pojo.dto;

import java.sql.Timestamp;

/**
 * @Author: WinstonDeng
 * @Description: OOAD_Course_ManageSystem
 * @Date: Created in 22:22 2018/12/18
 * @Modified by:
 */
public class UpdateSeminarDTO {

    /**
     * 讨论课名字
     */
    private String topic;
    /**
     * 介绍
     */
    private String intro;
    /**
     * 讨论课次序
     */
    private Integer serial;
    /**
     * 是否可见
     */
    private Integer visible;
    /**
     * 所属轮次
     */
    private Long roundId;
    /**
     * 报名开始时间
     */
    private Timestamp enrollStartTime;
    /**
     * 报名结束时间
     */
    private Timestamp enrollEndTime;
    /**
     * 报名组数
     */
    private Integer maxTeam;


    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }

    public Integer getSerial() {
        return serial;
    }

    public void setSerial(Integer serial) {
        this.serial = serial;
    }

    public Integer getVisible() {
        return visible;
    }

    public void setVisible(Integer visible) {
        this.visible = visible;
    }

    public Long getRoundId() {
        return roundId;
    }

    public void setRoundId(Long roundId) {
        this.roundId = roundId;
    }

    public Timestamp getEnrollStartTime() {
        return enrollStartTime;
    }

    public void setEnrollStartTime(Timestamp enrollStartTime) {
        this.enrollStartTime = enrollStartTime;
    }

    public Timestamp getEnrollEndTime() {
        return enrollEndTime;
    }

    public void setEnrollEndTime(Timestamp enrollEndTime) {
        this.enrollEndTime = enrollEndTime;
    }

    public Integer getMaxTeam() {
        return maxTeam;
    }

    public void setMaxTeam(Integer maxTeam) {
        this.maxTeam = maxTeam;
    }
}
