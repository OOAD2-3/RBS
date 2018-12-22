package com.rbs.project.pojo.entity;

import java.math.BigDecimal;

/**
 * @Author: WinstonDeng
 * @Description: OOAD_Course_ManageSystem
 * @Date: Created in 14:56 2018/12/15
 * @Modified by:
 */
public class RoundScore {
    /**
     * 轮次id
     */
    private long roundId;
    /**
     * 队伍id
     */
    private long teamId;
    /**
     * 轮次总分
     */
    private double totalScore;
    /**
     * 展示总分
     */
    private double presentationScore;
    /**
     * 报告总分
     */
    private double reportScore;
    /**
     * 提问总分
     */
    private double questionScore;

    //关系
    /**
     * 一个轮次
     */
    private Round round;
    /**
     * 一个队伍
     */
    private Team team;

    //==================================================getter AND setter==================================================//

    public long getRoundId() {
        return roundId;
    }

    public void setRoundId(long roundId) {
        this.roundId = roundId;
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

    public Round getRound() {
        return round;
    }

    public void setRound(Round round) {
        this.round = round;
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }
//================ toString ===========================

}
