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
    private double totalScore;
    /**
     * 本次讨论课展示分
     */
    private double presentationScore;
    /**
     * 本次讨论课报告分
     */
    private double reportScore;
    /**
     * 本次讨论课提问分
     */
    private double questionScore;

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

    public double getTotalScore() {
        return totalScore;
    }

    public void setTotalScore(double totalScore) {
        this.totalScore = totalScore;
    }

    public double getPresentationScore() {
        return presentationScore;
    }

    public void setPresentationScore(double presentationScore) {
        this.presentationScore = presentationScore;
    }

    public double getReportScore() {
        return reportScore;
    }

    public void setReportScore(double reportScore) {
        this.reportScore = reportScore;
    }

    public double getQuestionScore() {
        return questionScore;
    }

    public void setQuestionScore(double questionScore) {
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
