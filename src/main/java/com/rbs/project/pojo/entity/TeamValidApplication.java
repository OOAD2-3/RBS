package com.rbs.project.pojo.entity;

import java.math.BigInteger;

/**
 * @Author: WinstonDeng
 * @Description: OOAD_Course_ManageSystem
 * @Date: Created in 14:37 2018/12/15
 * @Modified by:
 */
public class TeamValidApplication {
    //基本信息

    private BigInteger id;
    /**
     * 队伍id
     */
    private BigInteger teamId;
    /**
     * 教师id
     */
    private BigInteger teacherId;
    /**
     * 申请原因
     */
    private String reason;
    /**
     * 请求状态，同意1、不同意0、未处理null
     */
    private Integer status;

    //关系
    /**
     * 一个队伍
     */
    private Team team;
    /**
     * 一个接收请求的老师
     */
    private Teacher teacher;

    //==================================================getter AND setter==================================================//

    public BigInteger getId() {
        return id;
    }

    public void setId(BigInteger id) {
        this.id = id;
    }

    public BigInteger getTeamId() {
        return teamId;
    }

    public void setTeamId(BigInteger teamId) {
        this.teamId = teamId;
    }

    public BigInteger getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(BigInteger teacherId) {
        this.teacherId = teacherId;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    //======================== toString =====================

    @Override
    public String toString() {
        return "TeamValidApplication{" +
                "id=" + id +
                ", teamId=" + teamId +
                ", teacherId=" + teacherId +
                ", reason='" + reason + '\'' +
                ", status=" + status +
                ", team=" + team +
                ", teacher=" + teacher +
                '}';
    }
}
