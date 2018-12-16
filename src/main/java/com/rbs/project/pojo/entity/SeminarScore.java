package com.rbs.project.pojo.entity;

import java.math.BigDecimal;

/**
 * @Author: WinstonDeng
 * @Description: OOAD_Course_ManageSystem
 * @Date: Created in 14:59 2018/12/15
 * @Modified by:
 */
public class SeminarScore {
    /**
     * 班级讨论课id
     */
    private long cClassSeminarId;
    /**
     * 队伍id
     */
    private long teamId;
    /**
     * 本次讨论课总分
     */
    private BigDecimal totalScore;
    /**
     * 本次讨论课展示分
     */
    private BigDecimal presentationScore;
    /**
     * 本次讨论课报告分
     */
    private BigDecimal reportScore;
    /**
     * 本次讨论课提问分
     */
    private BigDecimal questionScore;

    //关系
    /**
     * 一个班级讨论课
     */
    private CClassSeminar cClassSeminar;
    /**
     * 一个队伍
     */
    private Team team;

    //==================================================getter AND setter==================================================//

    public long getcClassSeminarId() {
        return cClassSeminarId;
    }

    public void setcClassSeminarId(long cClassSeminarId) {
        this.cClassSeminarId = cClassSeminarId;
    }

    public long getTeamId() {
        return teamId;
    }

    public void setTeamId(long teamId) {
        this.teamId = teamId;
    }

    public BigDecimal getTotalScore() {
        return totalScore;
    }

    public void setTotalScore(BigDecimal totalScore) {
        this.totalScore = totalScore;
    }

    public BigDecimal getPresentationScore() {
        return presentationScore;
    }

    public void setPresentationScore(BigDecimal presentationScore) {
        this.presentationScore = presentationScore;
    }

    public BigDecimal getReportScore() {
        return reportScore;
    }

    public void setReportScore(BigDecimal reportScore) {
        this.reportScore = reportScore;
    }

    public BigDecimal getQuestionScore() {
        return questionScore;
    }

    public void setQuestionScore(BigDecimal questionScore) {
        this.questionScore = questionScore;
    }

    public CClassSeminar getcClassSeminar() {
        return cClassSeminar;
    }

    public void setcClassSeminar(CClassSeminar cClassSeminar) {
        this.cClassSeminar = cClassSeminar;
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }
//====================== toString ====================

}
