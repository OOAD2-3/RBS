package com.rbs.project.pojo.entity;

import java.math.BigInteger;
import java.util.List;

/**
 * @Author:17Wang
 * @Date:22:20 2018/12/4
 * @Description:
 */
public class Round {

    private BigInteger id;
    /**
     * 所属课程id
     */
    private BigInteger courseId;
    /**
     * 第几轮次
     */
    private Integer serial;
    /**
     * 展示成绩计算办法
     */
    private Integer presentationScoreMethod;
    /**
     * 报告成绩计算办法
     */
    private Integer reportScoreMethod;
    /**
     * 提问成绩计算办法
     */
    private Integer questionScoreMethod;

    //关系
    /**
     * 一个课程
     */
    private Course course;
    /**
     * 多个讨论课
     */
    private List<Seminar> seminars;
    /**
     * 多个小组的轮次成绩
     */
    private List<RoundScore> roundScores;
    /**
     * 多个班级轮次
     */
    private List<CClassRound> cClassRounds;
    //==================================================getter AND setter==================================================//


    public BigInteger getId() {
        return id;
    }

    public void setId(BigInteger id) {
        this.id = id;
    }

    public BigInteger getCourseId() {
        return courseId;
    }

    public void setCourseId(BigInteger courseId) {
        this.courseId = courseId;
    }

    public Integer getSerial() {
        return serial;
    }

    public void setSerial(Integer serial) {
        this.serial = serial;
    }

    public Integer getPresentationScoreMethod() {
        return presentationScoreMethod;
    }

    public void setPresentationScoreMethod(Integer presentationScoreMethod) {
        this.presentationScoreMethod = presentationScoreMethod;
    }

    public Integer getReportScoreMethod() {
        return reportScoreMethod;
    }

    public void setReportScoreMethod(Integer reportScoreMethod) {
        this.reportScoreMethod = reportScoreMethod;
    }

    public Integer getQuestionScoreMethod() {
        return questionScoreMethod;
    }

    public void setQuestionScoreMethod(Integer questionScoreMethod) {
        this.questionScoreMethod = questionScoreMethod;
    }

    //============= toString =========================================

    @Override
    public String toString() {
        return "Round{" +
                "id=" + id +
                ", courseId=" + courseId +
                ", serial=" + serial +
                ", presentationScoreMethod=" + presentationScoreMethod +
                ", reportScoreMethod=" + reportScoreMethod +
                ", questionScoreMethod=" + questionScoreMethod +
                ", course=" + course +
                ", seminars=" + seminars +
                ", roundScores=" + roundScores +
                ", cClassRounds=" + cClassRounds +
                '}';
    }
}
