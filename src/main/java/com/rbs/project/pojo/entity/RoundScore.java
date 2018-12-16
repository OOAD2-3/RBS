package com.rbs.project.pojo.entity;

import java.math.BigDecimal;
import java.math.BigInteger;

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
    private BigInteger roundId;
    /**
     * 队伍id
     */
    private BigInteger teamId;
    /**
     * 轮次总分
     */
    private BigDecimal totalScore;
    /**
     * 展示总分
     */
    private BigDecimal presentationScore;
    /**
     * 报告总分
     */
    private BigDecimal reportScore;
    /**
     * 提问总分
     */
    private BigDecimal questionScore;

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

    public BigInteger getRoundId() {
        return roundId;
    }

    public void setRoundId(BigInteger roundId) {
        this.roundId = roundId;
    }

    public BigInteger getTeamId() {
        return teamId;
    }

    public void setTeamId(BigInteger teamId) {
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
