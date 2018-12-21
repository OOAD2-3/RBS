package com.rbs.project.pojo.vo;

import com.rbs.project.pojo.entity.Course;
import com.rbs.project.pojo.strategy.CourseMemberLimitStrategy;
import com.rbs.project.utils.JsonUtils;

import java.sql.Timestamp;
import java.util.List;

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
    private Integer presentationPercentage;
    private Integer questionPercentage;
    private Integer reportPercentage;
    private String teamStartTime;
    private String teamEndTime;

    private CourseMemberLimitStrategy courseMemberLimitStrategy;

    private List<Long> conflictCourses;

    private boolean ShareTeam;
    private boolean ShareSeminar;

    public CourseAndStrategyVO() {

    }

    public CourseAndStrategyVO(Course course) {
        id = course.getId();
        name = course.getName();
        intro = course.getIntro();
        presentationPercentage = course.getPresentationPercentage();
        questionPercentage = course.getQuestionPercentage();
        reportPercentage = course.getReportPercentage();
        teamStartTime = JsonUtils.TimestampToString(course.getTeamStartTime());
        teamEndTime = JsonUtils.TimestampToString(course.getTeamEndTime());

        courseMemberLimitStrategy = course.getCourseMemberLimitStrategy();
        conflictCourses = course.getConflictCourses();
        if (course.getTeamMainCourseId() == 0) {
            ShareTeam = false;
        }
        if (course.getSeminarMainCourseId() == 0) {
            ShareSeminar = false;
        }
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

    public String getTeamStartTime() {
        return teamStartTime;
    }

    public void setTeamStartTime(String teamStartTime) {
        this.teamStartTime = teamStartTime;
    }

    public String getTeamEndTime() {
        return teamEndTime;
    }

    public void setTeamEndTime(String teamEndTime) {
        this.teamEndTime = teamEndTime;
    }

    public CourseMemberLimitStrategy getCourseMemberLimitStrategy() {
        return courseMemberLimitStrategy;
    }

    public void setCourseMemberLimitStrategy(CourseMemberLimitStrategy courseMemberLimitStrategy) {
        this.courseMemberLimitStrategy = courseMemberLimitStrategy;
    }

    public List<Long> getConflictCourses() {
        return conflictCourses;
    }

    public void setConflictCourses(List<Long> conflictCourses) {
        this.conflictCourses = conflictCourses;
    }

    public boolean isShareTeam() {
        return ShareTeam;
    }

    public void setShareTeam(boolean shareTeam) {
        ShareTeam = shareTeam;
    }

    public boolean isShareSeminar() {
        return ShareSeminar;
    }

    public void setShareSeminar(boolean shareSeminar) {
        ShareSeminar = shareSeminar;
    }
}