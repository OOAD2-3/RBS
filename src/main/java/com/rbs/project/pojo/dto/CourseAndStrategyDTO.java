package com.rbs.project.pojo.dto;

import com.rbs.project.pojo.entity.Course;
import com.rbs.project.pojo.strategy.CourseMemberLimitStrategy;
import com.rbs.project.pojo.strategy.MemberLimitStrategy;
import com.rbs.project.pojo.vo.CourseInfoVO;
import com.rbs.project.utils.JsonUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Description:
 *
 * @Author: 17Wang
 * @Date: 23:15 2018/12/18
 */
public class CourseAndStrategyDTO {
    private Long id;
    private String name;
    private String intro;
    private Integer presentationPercentage;
    private Integer questionPercentage;
    private Integer reportPercentage;
    private String teamStartTime;
    private String teamEndTime;

    private MemberLimitStrategy memberLimitStrategy;
    private List<CourseMemberLimitStrategy> courseMemberLimitStrategies;
    private List<List<Long>> conflictCourses;
    private Integer courseMemberLimitFlag;

    private Boolean ShareTeam;
    private Boolean ShareSeminar;

    public CourseAndStrategyDTO() {

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

    public MemberLimitStrategy getMemberLimitStrategy() {
        return memberLimitStrategy;
    }

    public void setMemberLimitStrategy(MemberLimitStrategy memberLimitStrategy) {
        this.memberLimitStrategy = memberLimitStrategy;
    }

    public List<CourseMemberLimitStrategy> getCourseMemberLimitStrategies() {
        return courseMemberLimitStrategies;
    }

    public void setCourseMemberLimitStrategies(List<CourseMemberLimitStrategy> courseMemberLimitStrategies) {
        this.courseMemberLimitStrategies = courseMemberLimitStrategies;
    }

    public Boolean isShareTeam() {
        return ShareTeam;
    }

    public void setShareTeam(Boolean shareTeam) {
        ShareTeam = shareTeam;
    }

    public Boolean isShareSeminar() {
        return ShareSeminar;
    }

    public void setShareSeminar(Boolean shareSeminar) {
        ShareSeminar = shareSeminar;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<List<Long>> getConflictCourses() {
        return conflictCourses;
    }

    public void setConflictCourses(List<List<Long>> conflictCourses) {
        this.conflictCourses = conflictCourses;
    }

    public Boolean getShareTeam() {
        return ShareTeam;
    }

    public Boolean getShareSeminar() {
        return ShareSeminar;
    }

    public Integer getCourseMemberLimitFlag() {
        return courseMemberLimitFlag;
    }

    public void setCourseMemberLimitFlag(Integer courseMemberLimitFlag) {
        this.courseMemberLimitFlag = courseMemberLimitFlag;
    }
}