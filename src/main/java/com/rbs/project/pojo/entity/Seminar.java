package com.rbs.project.pojo.entity;

import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.List;

/**
 * @Author:17Wang
 * @Date:22:20 2018/12/4
 * @Description:
 */
public class Seminar {

    private BigInteger id;
    /**
     * 属于哪个round
     */
    private BigInteger roundId;
    /**
     * 属于哪个course（目前只存主课程）
     */
    private BigInteger courseId;
    /**
     * 讨论课主题
     */
    private String name;
    /**
     * 讨论课介绍
     */
    private String intro;
    /**
     * 报名讨论课最多组数
     */
    private Integer maxTeam;
    /**
     * 是否可见
     */
    private Integer visible;
    /**
     * 讨论课序号
     */
    private Integer serial;
    /**
     * 报名开始时间
     */
    private Timestamp enrollStartTime;
    /**
     * 报名结束时间
     */
    private Timestamp enrollEndTime;

    //关系
    /**
     * 一个轮次
     */
    private Round round;
    /**
     * 一个课程(目前只存主课程)
     */
    private Course course;
    /**
     * 多个班级讨论课
     */
    private List<CClassSeminar> cClassSeminars;

    //==================================================getter AND setter==================================================//

    public BigInteger getId() {
        return id;
    }

    public void setId(BigInteger id) {
        this.id = id;
    }

    public BigInteger getRoundId() {
        return roundId;
    }

    public void setRoundId(BigInteger roundId) {
        this.roundId = roundId;
    }

    public BigInteger getCourseId() {
        return courseId;
    }

    public void setCourseId(BigInteger courseId) {
        this.courseId = courseId;
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

    public Integer getMaxTeam() {
        return maxTeam;
    }

    public void setMaxTeam(Integer maxTeam) {
        this.maxTeam = maxTeam;
    }

    public Integer getVisible() {
        return visible;
    }

    public void setVisible(Integer visible) {
        this.visible = visible;
    }

    public Integer getSerial() {
        return serial;
    }

    public void setSerial(Integer serial) {
        this.serial = serial;
    }

    public Timestamp getEnrollStartTime() {
        return enrollStartTime;
    }

    public void setEnrollStartTime(Timestamp enrollStartTime) {
        this.enrollStartTime = enrollStartTime;
    }

    public Timestamp getEnrollEndTime() {
        return enrollEndTime;
    }

    public void setEnrollEndTime(Timestamp enrollEndTime) {
        this.enrollEndTime = enrollEndTime;
    }

    public Round getRound() {
        return round;
    }

    public void setRound(Round round) {
        this.round = round;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public List<CClassSeminar> getcClassSeminars() {
        return cClassSeminars;
    }

    public void setcClassSeminars(List<CClassSeminar> cClassSeminars) {
        this.cClassSeminars = cClassSeminars;
    }
//================ toString ========================

}