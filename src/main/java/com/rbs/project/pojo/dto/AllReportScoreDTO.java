package com.rbs.project.pojo.dto;

import com.rbs.project.pojo.entity.SeminarScore;

/**
 * Description:
 *
 * @Author: 17Wang
 * @Date: 9:28 2018/12/29
 */
public class AllReportScoreDTO {
    private Long teamId;
    private Double reportScore;

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
}
