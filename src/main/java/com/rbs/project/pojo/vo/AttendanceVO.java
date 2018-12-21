package com.rbs.project.pojo.vo;

import com.rbs.project.pojo.entity.Attendance;
import com.rbs.project.utils.JsonUtils;

/**
 * Description:
 *
 * @Author: 17Wang
 * @Date: 17:20 2018/12/21
 */
public class AttendanceVO {
    private long id;
    private Integer present;
    private String pptName;
    private String pptUrl;
    private String reportName;
    private String reportUrl;
    private String reportDDL;
    private TeamBaseInfoVO teamBaseInfoVO;

    public AttendanceVO(){

    }

    public AttendanceVO(Attendance attendance) {
        id = attendance.getTeamId();
        present = attendance.getPresent();
        pptName = attendance.getPptName();
        pptUrl = attendance.getPptUrl();
        reportName = attendance.getReportName();
        reportUrl = attendance.getReportUrl();
        reportDDL = JsonUtils.TimestampToString(attendance.getcClassSeminar().getReportDDL());
        teamBaseInfoVO = new TeamBaseInfoVO(attendance.getTeam());
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    public String getReportDDL() {
        return reportDDL;
    }

    public void setReportDDL(String reportDDL) {
        this.reportDDL = reportDDL;
    }

    public TeamBaseInfoVO getTeamBaseInfoVO() {
        return teamBaseInfoVO;
    }

    public void setTeamBaseInfoVO(TeamBaseInfoVO teamBaseInfoVO) {
        this.teamBaseInfoVO = teamBaseInfoVO;
    }
}
