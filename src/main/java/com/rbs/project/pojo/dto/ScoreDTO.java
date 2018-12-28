package com.rbs.project.pojo.dto;

import com.rbs.project.pojo.vo.ScoreVO;

import java.util.List;

/**
 * Description:
 *
 * @Author: 17Wang
 * @Date: 4:10 2018/12/29
 */
public class ScoreDTO {
    private Long teamId;
    private Double reportScore;
    private Double questionScore;
    private Double presentationScore;

    public Long getTeamId() {
        return teamId;
    }

    public void setTeamId(Long teamId) {
        this.teamId = teamId;
    }

    public Double getReportScore() {
        return reportScore;
    }

    public void setReportScore(Double reportScore) {
        this.reportScore = reportScore;
    }

    public Double getQuestionScore() {
        return questionScore;
    }

    public void setQuestionScore(Double questionScore) {
        this.questionScore = questionScore;
    }

    public Double getPresentationScore() {
        return presentationScore;
    }

    public void setPresentationScore(Double presentationScore) {
        this.presentationScore = presentationScore;
    }
}
