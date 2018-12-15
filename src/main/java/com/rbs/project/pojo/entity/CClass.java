package com.rbs.project.pojo.entity;

import java.math.BigInteger;
import java.util.List;

/**
 * @Author:17Wang
 * @Date:22:20 2018/12/4
 * @Description: 对应表klass
 */
public class CClass {
    //基本信息

    private BigInteger id;
    /**
     * 属于哪个课程
     */
    private BigInteger courseId;
    /**
     * 年级
     */
    private Integer grade;
    /**
     * 班级序号
     */
    private Integer serial;
    /**
     * 上课地点
     */
    private String place;
    /**
     * 上课时间
     */
    private String time;

    //关系
    /**
     * 一个课程
     */
    private Course course;
    /**
     * 多个班级讨论课
     */
    private List<CClassSeminar> cClassSeminars;
    /**
     * 多个队伍
     */
    private List<Team> teams;
    /**
     * 多个班级学生
     */
    private List<CClassStudent> cClassStudents;
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

    public Integer getGrade() {
        return grade;
    }

    public void setGrade(Integer grade) {
        this.grade = grade;
    }

    public Integer getSerial() {
        return serial;
    }

    public void setSerial(Integer serial) {
        this.serial = serial;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    //================ toString ============================

    @Override
    public String toString() {
        return "CClass{" +
                "id=" + id +
                ", courseId=" + courseId +
                ", grade=" + grade +
                ", serial=" + serial +
                ", place='" + place + '\'' +
                ", time='" + time + '\'' +
                ", course=" + course +
                ", cClassSeminars=" + cClassSeminars +
                ", teams=" + teams +
                ", cClassStudents=" + cClassStudents +
                ", cClassRounds=" + cClassRounds +
                '}';
    }
}
