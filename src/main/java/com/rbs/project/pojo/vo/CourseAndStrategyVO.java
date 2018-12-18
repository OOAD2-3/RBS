package com.rbs.project.pojo.vo;

import com.rbs.project.pojo.entity.Course;
import com.rbs.project.pojo.strategy.CourseMemberLimitStrategy;

import java.sql.Timestamp;

/**
 * Description:
 *
 * @Author: 17Wang
 * @Date: 23:15 2018/12/18
 */
public class CourseAndStrategyVO {
    private long id;
    private String name;
    private String intro;
    private int presentationPercentage;
    private int questionPercentage;
    private int reportPercentage;
    private Timestamp teamStartTime;
    private Timestamp teamEndTime;

    private CourseMemberLimitStrategy courseMemberLimitStrategy;

    public CourseAndStrategyVO(Course course){
        id=course.getId();
        name=course.getName();
        intro=course.getIntro();
        presentationPercentage=course.getPresentationPercentage();
        questionPercentage=course.getQuestionPercentage();
        reportPercentage=course.getReportPercentage();
        teamStartTime=course.getTeamStartTime();
        teamEndTime=course.getTeamEndTime();

        courseMemberLimitStrategy=course.getCourseMemberLimitStrategy();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    public int getPresentationPercentage() {
        return presentationPercentage;
    }

    public void setPresentationPercentage(int presentationPercentage) {
        this.presentationPercentage = presentationPercentage;
    }

    public int getQuestionPercentage() {
        return questionPercentage;
    }

    public void setQuestionPercentage(int questionPercentage) {
        this.questionPercentage = questionPercentage;
    }

    public int getReportPercentage() {
        return reportPercentage;
    }

    public void setReportPercentage(int reportPercentage) {
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

    public CourseMemberLimitStrategy getCourseMemberLimitStrategy() {
        return courseMemberLimitStrategy;
    }

    public void setCourseMemberLimitStrategy(CourseMemberLimitStrategy courseMemberLimitStrategy) {
        this.courseMemberLimitStrategy = courseMemberLimitStrategy;
    }
}