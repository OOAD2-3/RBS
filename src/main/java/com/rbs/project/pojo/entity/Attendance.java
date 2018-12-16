package com.rbs.project.pojo.entity;

import java.math.BigInteger;
import java.util.List;

/**
 * @Author:17Wang
 * @Date:22:20 2018/12/4
 * @Description: 对应表attendance
 */
public class Attendance {
    //基础信息

    private BigInteger id;
    /**
     * 班级讨论课id
     */
    private BigInteger cClassSeminarId;
    /**
     * 队伍id
     */
    private BigInteger teamId;
    /**
     * 队伍次序
     */
    private Integer teamOrder;
    /**
     * 是否在展示
     */
    private Integer present;
    /**
     * ppt文件名
     */
    private String pptName;
    /**
     * ppt文件路径
     */
    private String pptUrl;
    /**
     * 报告文件名
     */
    private String reportName;
    /**
     * 报告文件路径
     */
    private String reportUrl;

    //关系
    /**
     * 一个班级讨论课
     */
    private CClassSeminar cClassSeminar;
    /**
     * 一个队伍
     */
    private Team team;
    /**
     * 多个提问
     */
    private List<Question> questions;
    //==================================================getter AND setter==================================================//

    public BigInteger getId() {
        return id;
    }

    public void setId(BigInteger id) {
        this.id = id;
    }

    public BigInteger getcClassSeminarId() {
        return cClassSeminarId;
    }

    public void setcClassSeminarId(BigInteger cClassSeminarId) {
        this.cClassSeminarId = cClassSeminarId;
    }

    public BigInteger getTeamId() {
        return teamId;
    }

    public void setTeamId(BigInteger teamId) {
        this.teamId = teamId;
    }

    public Integer getTeamOrder() {
        return teamOrder;
    }

    public void setTeamOrder(Integer teamOrder) {
        this.teamOrder = teamOrder;
    }

    public Integer getPresent() {
        return present;
    }

    public void setPresent(Integer present) {
        this.present = present;
    }

    public String getPptName() {
        return pptName;
    }

    public void setPptName(String pptName) {
        this.pptName = pptName;
    }

    public String getPptUrl() {
        return pptUrl;
    }

    public void setPptUrl(String pptUrl) {
        this.pptUrl = pptUrl;
    }

    public String getReportName() {
        return reportName;
    }

    public void setReportName(String reportName) {
        this.reportName = reportName;
    }

    public String getReportUrl() {
        return reportUrl;
    }

    public void setReportUrl(String reportUrl) {
        this.reportUrl = reportUrl;
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

    public List<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }
//================= toString =======================

}
