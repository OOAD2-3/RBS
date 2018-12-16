package com.rbs.project.pojo.entity;

import java.math.BigDecimal;
import java.math.BigInteger;

/**
 * @Author:17Wang
 * @Date:22:20 2018/12/4
 * @Description:
 */
public class Question {
    //基本信息

    private BigInteger id;
    /**
     * 班级讨论课id
     */
    private BigInteger cClassSeminarId;
    /**
     * 问题所针对的发言id
     */
    private BigInteger attendanceId;
    /**
     * 属于哪个队伍
     */
    private BigInteger teamId;
    /**
     * 学生id
     */
    private BigInteger studentId;
    /**
     * 提问分数
     */
    private BigDecimal score;
    /**
     * 是否被提问
     */
    private Integer selected;

    //关系
    /**
     * 一个班级讨论课
     */
    private CClassSeminar cClassSeminar;
    /**
     * 一个被提问的展示
     */
    private Attendance attendance;
    /**
     * 一个发起提问的队伍
     */
    private Team team;
    /**
     * 一个发起提问的学生
     */
    private Student student;

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

    public BigInteger getAttendanceId() {
        return attendanceId;
    }

    public void setAttendanceId(BigInteger attendanceId) {
        this.attendanceId = attendanceId;
    }

    public BigInteger getTeamId() {
        return teamId;
    }

    public void setTeamId(BigInteger teamId) {
        this.teamId = teamId;
    }

    public BigInteger getStudentId() {
        return studentId;
    }

    public void setStudentId(BigInteger studentId) {
        this.studentId = studentId;
    }

    public BigDecimal getScore() {
        return score;
    }

    public void setScore(BigDecimal score) {
        this.score = score;
    }

    public Integer getSelected() {
        return selected;
    }

    public void setSelected(Integer selected) {
        this.selected = selected;
    }

    public CClassSeminar getcClassSeminar() {
        return cClassSeminar;
    }

    public void setcClassSeminar(CClassSeminar cClassSeminar) {
        this.cClassSeminar = cClassSeminar;
    }

    public Attendance getAttendance() {
        return attendance;
    }

    public void setAttendance(Attendance attendance) {
        this.attendance = attendance;
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }
//====================== toString =========================

}
