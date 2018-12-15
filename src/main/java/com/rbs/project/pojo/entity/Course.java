package com.rbs.project.pojo.entity;

import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.List;

/**
 * @Author:17Wang
 * @Date:22:20 2018/12/4
 * @Description:
 */
public class Course {
    //基本信息

    private BigInteger id;
    /**
     * 属于哪个老师
     */
    private BigInteger teacherId;
    /**
     * 课程名
     */
    private String name;
    /**
     * 课程介绍
     */
    private String intro;
    /**
     * 展示分数百分比（这里放0~100整数，前端处理为百分数，下同）
     */
    private Integer presentationPercentage;
    /**
     * 提问分数百分比
     */
    private Integer questionPercentage;
    /**
     * 报告分数百分比
     */
    private Integer reportPercentage;
    /**
     * 组队开始时间
     */
    private Timestamp teamStartTime;
    /**
     * 组队结束时间
     */
    private Timestamp teamEndTime;
    /**
     * 队伍共享主课程
     */
    private BigInteger teamMainCourseId;
    /**
     * 讨论课共享主课程
     */
    private BigInteger seminarMainCourseId;

    //关系
    /**
     * 一个老师
     */
    private Teacher teacher;
    /**
     * 一个队伍共享主课程
     */
    private Course teamMainCourse;
    /**
     * 一个讨论课共享主课程
     */
    private Course seminarMainCourse;
    /**
     * 多个讨论课
     */
    private List<Seminar> seminars;
    /**
     * 多个轮次
     */
    private List<Round> rounds;
    /**
     * 多个班级
     */
    private List<CClass> cClasses;
    /**
     * 多个队伍
     */
    private List<Team> teams;
    /**
     * 多个班级学生
     */
    private List<CClassStudent> cClassStudents;

    //==================================================getter AND setter==================================================//


    public BigInteger getId() {
        return id;
    }

    public void setId(BigInteger id) {
        this.id = id;
    }

    public BigInteger getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(BigInteger teacherId) {
        this.teacherId = teacherId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }

    public Integer getPresentationPercentage() {
        return presentationPercentage;
    }

    public void setPresentationPercentage(Integer presentationPercentage) {
        this.presentationPercentage = presentationPercentage;
    }

    public Integer getQuestionPercentage() {
        return questionPercentage;
    }

    public void setQuestionPercentage(Integer questionPercentage) {
        this.questionPercentage = questionPercentage;
    }

    public Integer getReportPercentage() {
        return reportPercentage;
    }

    public void setReportPercentage(Integer reportPercentage) {
        this.reportPercentage = reportPercentage;
    }

    public Timestamp getTeamStartTime() {
        return teamStartTime;
    }

    public void setTeamStartTime(Timestamp teamStartTime) {
        this.teamStartTime = teamStartTime;
    }

    public Timestamp getTeamEndTime() {
        return teamEndTime;
    }

    public void setTeamEndTime(Timestamp teamEndTime) {
        this.teamEndTime = teamEndTime;
    }

    public BigInteger getTeamMainCourseId() {
        return teamMainCourseId;
    }

    public void setTeamMainCourseId(BigInteger teamMainCourseId) {
        this.teamMainCourseId = teamMainCourseId;
    }

    public BigInteger getSeminarMainCourseId() {
        return seminarMainCourseId;
    }

    public void setSeminarMainCourseId(BigInteger seminarMainCourseId) {
        this.seminarMainCourseId = seminarMainCourseId;
    }

    //======================= toString=============================


    @Override
    public String toString() {
        return "Course{" +
                "id=" + id +
                ", teacherId=" + teacherId +
                ", name='" + name + '\'' +
                ", intro='" + intro + '\'' +
                ", presentationPercentage=" + presentationPercentage +
                ", questionPercentage=" + questionPercentage +
                ", reportPercentage=" + reportPercentage +
                ", teamStartTime=" + teamStartTime +
                ", teamEndTime=" + teamEndTime +
                ", teamMainCourseId=" + teamMainCourseId +
                ", seminarMainCourseId=" + seminarMainCourseId +
                ", teacher=" + teacher +
                ", teamMainCourse=" + teamMainCourse +
                ", seminarMainCourse=" + seminarMainCourse +
                ", seminars=" + seminars +
                ", rounds=" + rounds +
                ", cClasses=" + cClasses +
                ", teams=" + teams +
                ", cClassStudents=" + cClassStudents +
                '}';
    }
}
