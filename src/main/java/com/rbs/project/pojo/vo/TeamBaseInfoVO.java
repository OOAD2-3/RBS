package com.rbs.project.pojo.vo;

import com.rbs.project.pojo.entity.Team;

/**
 * Description:
 *
 * @Author: 17Wang
 * @Date: 17:07 2018/12/21
 */
public class TeamBaseInfoVO {
    private long id;
    private String TeamSerials;
    private String TeamName;

    public TeamBaseInfoVO(Team team) {
        id = team.getId();
        TeamSerials = team.getcClass().getSerial() + "-" + String.valueOf(team.getSerial());
        TeamName = team.getName();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTeamSerials() {
        return TeamSerials;
    }

    public void setTeamSerials(String teamSerials) {
        TeamSerials = teamSerials;
    }

    public String getTeamName() {
        return TeamName;
    }

    public void setTeamName(String teamName) {
        TeamName = teamName;
    }
}
