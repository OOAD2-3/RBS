package com.rbs.project.pojo.vo;

import com.rbs.project.pojo.entity.RoundScore;
import com.rbs.project.pojo.entity.SeminarScore;

/**
 * Description:
 *
 * @Author: 17Wang
 * @Date: 16:27 2018/12/22
 */
public class ScoreVO {
    private TeamBaseInfoVO teamBaseInfoVO;
    private double totalScore;
    private double reportScore;
    private double questionScore;
    private double presentationScore;

    public ScoreVO revertSeminarScore(SeminarScore seminarScore) {
        teamBaseInfoVO = new TeamBaseInfoVO(seminarScore.getTeam());
        totalScore=seminarScore.getTotalScore();
        reportScore=seminarScore.getReportScore();
        questionScore=seminarScore.getQuestionScore();
        presentationScore=seminarScore.getPresentationScore();
        return this;
    }

    public ScoreVO revertSeminarScore(RoundScore roundScore) {
        teamBaseInfoVO = new TeamBaseInfoVO(roundScore.getTeam());
        totalScore=roundScore.getTotalScore();
        reportScore=roundScore.getReportScore();
        questionScore=roundScore.getQuestionScore();
        presentationScore=roundScore.getPresentationScore();
        return this;
    }

    public TeamBaseInfoVO getTeamBaseInfoVO() {
        return teamBaseInfoVO;
    }

    public void setTeamBaseInfoVO(TeamBaseInfoVO teamBaseInfoVO) {
        this.teamBaseInfoVO = teamBaseInfoVO;
    }

    public double getTotalScore() {
        return totalScore;
    }

    public void setTotalScore(double totalScore) {
        this.totalScore = totalScore;
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

    public double getPresentationScore() {
        return presentationScore;
    }

    public void setPresentationScore(double presentationScore) {
        this.presentationScore = presentationScore;
    }
}
