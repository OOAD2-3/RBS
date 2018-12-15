package com.rbs.project.pojo.entity;

import java.math.BigInteger;

/**
 * @Author: WinstonDeng
 * @Description: OOAD_Course_ManageSystem
 * @Date: Created in 15:11 2018/12/15
 * @Modified by:
 */
public class CClassStudent {
    /**
     * 班级id
     */
    private BigInteger cClassId;
    /**
     * 学生id
     */
    private BigInteger studentId;
    /**
     * 课程id
     */
    private BigInteger courseId;
    /**
     * 队伍id
     */
    private BigInteger teamId;

    //关系
    /**
     * 一个（导入学生名单的）班级
     */
    private CClass cClass;
    /**
     * 一个（导入学生名单的）课程
     */
    private Course course;
    /**
     * 一个队伍
     */
    private Team team;
    /**
     * 一个学生
     */
    private Student student;

    //==================================================getter AND setter==================================================//


    public BigInteger getcClassId() {
        return cClassId;
    }

    public void setcClassId(BigInteger cClassId) {
        this.cClassId = cClassId;
    }

    public BigInteger getStudentId() {
        return studentId;
    }

    public void setStudentId(BigInteger studentId) {
        this.studentId = studentId;
    }

    public BigInteger getCourseId() {
        return courseId;
    }

    public void setCourseId(BigInteger courseId) {
        this.courseId = courseId;
    }

    public BigInteger getTeamId() {
        return teamId;
    }

    public void setTeamId(BigInteger teamId) {
        this.teamId = teamId;
    }

    //====================== toString ==================

    @Override
    public String toString() {
        return "CClassStudent{" +
                "cClassId=" + cClassId +
                ", studentId=" + studentId +
                ", courseId=" + courseId +
                ", teamId=" + teamId +
                ", cClass=" + cClass +
                ", course=" + course +
                ", team=" + team +
                ", student=" + student +
                '}';
    }
}
