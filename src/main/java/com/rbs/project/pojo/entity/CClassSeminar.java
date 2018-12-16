package com.rbs.project.pojo.entity;

import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.List;

/**
 * @Author: WinstonDeng
 * @Description: OOAD_Course_ManageSystem
 * @Date: Created in 15:05 2018/12/15
 * @Modified by:
 */
public class CClassSeminar {
    private BigInteger id;
    /**
     * 班级id
     */
    private BigInteger cClassId;
    /**
     * 讨论课id
     */
    private BigInteger seminarId;
    /**
     * 报告截止日期
     */
    private Timestamp reportDDL;
    /**
     * 讨论课所处状态，未开始0，正在进行1，已结束2，暂停3
     */
    private Integer status;

    //关系
    /**
     * 一个班级
     */
    private CClass cClass;
    /**
     * 一个讨论课
     */
    private Seminar seminar;
    /**
     * 多个展示
     */
    private List<Attendance> attendances;
    /**
     * 多个提问
     */
    private List<Question> questions;
    /**
     * 多个小组讨论课成绩
     */
    private List<SeminarScore> seminarScores;

    //==================================================getter AND setter==================================================//

    public BigInteger getId() {
        return id;
    }

    public void setId(BigInteger id) {
        this.id = id;
    }

    public BigInteger getcClassId() {
        return cClassId;
    }

    public void setcClassId(BigInteger cClassId) {
        this.cClassId = cClassId;
    }

    public BigInteger getSeminarId() {
        return seminarId;
    }

    public void setSeminarId(BigInteger seminarId) {
        this.seminarId = seminarId;
    }

    public Timestamp getReportDDL() {
        return reportDDL;
    }

    public void setReportDDL(Timestamp reportDDL) {
        this.reportDDL = reportDDL;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public CClass getcClass() {
        return cClass;
    }

    public void setcClass(CClass cClass) {
        this.cClass = cClass;
    }

    public Seminar getSeminar() {
        return seminar;
    }

    public void setSeminar(Seminar seminar) {
        this.seminar = seminar;
    }

    public List<Attendance> getAttendances() {
        return attendances;
    }

    public void setAttendances(List<Attendance> attendances) {
        this.attendances = attendances;
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }

    public List<SeminarScore> getSeminarScores() {
        return seminarScores;
    }

    public void setSeminarScores(List<SeminarScore> seminarScores) {
        this.seminarScores = seminarScores;
    }
//=================== toString ========================

}