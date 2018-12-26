package com.rbs.project.pojo.entity;

import com.rbs.project.pojo.strategy.CourseMemberLimitStrategy;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;

/**
 * @Author:17Wang
 * @Date:22:20 2018/12/4
 * @Description:
 */
public class Course {
    /**
     * 基本信息
     */
    private long id;
    /**
     * 属于哪个老师
     */
    private long teacherId;
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
    private long teamMainCourseId;
    /**
     * 讨论课共享主课程
     */
    private long seminarMainCourseId;

    //关系
    /**
     * 课程组队限制
     */
    private CourseMemberLimitStrategy courseMemberLimitStrategy;
    /**
     * 冲突的课程们的id
     */
    private List<Course> conflictCourses;
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
    private List<Student> students;


    //==================================================getter AND setter==================================================//

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(long teacherId) {
        this.teacherId = teacherId;
    }

    public void setTeamMainCourseId(long teamMainCourseId) {
        this.teamMainCourseId = teamMainCourseId;
    }

    public void setSeminarMainCourseId(long seminarMainCourseId) {
        this.seminarMainCourseId = seminarMainCourseId;
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

    public long getTeamMainCourseId() {
        return teamMainCourseId;
    }

    public long getSeminarMainCourseId() {
        return seminarMainCourseId;
    }

    public CourseMemberLimitStrategy getCourseMemberLimitStrategy() {
        return courseMemberLimitStrategy;
    }

    public void setCourseMemberLimitStrategy(CourseMemberLimitStrategy courseMemberLimitStrategy) {
        this.courseMemberLimitStrategy = courseMemberLimitStrategy;
    }

    public List<Course> getConflictCourses() {
        return conflictCourses;
    }

    public void setConflictCourses(List<Course> conflictCourses) {
        this.conflictCourses = conflictCourses;
    }

    public Teacher getTeacher() {
        return teacher;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }

    public Course getTeamMainCourse() {
        return teamMainCourse;
    }

    public void setTeamMainCourse(Course teamMainCourse) {
        this.teamMainCourse = teamMainCourse;
    }

    public Course getSeminarMainCourse() {
        return seminarMainCourse;
    }

    public void setSeminarMainCourse(Course seminarMainCourse) {
        this.seminarMainCourse = seminarMainCourse;
    }

    public List<Seminar> getSeminars() {
        return seminars;
    }

    public void setSeminars(List<Seminar> seminars) {
        this.seminars = seminars;
    }

    public List<Round> getRounds() {
        return rounds;
    }

    public void setRounds(List<Round> rounds) {
        this.rounds = rounds;
    }

    public List<CClass> getcClasses() {
        return cClasses;
    }

    public void setcClasses(List<CClass> cClasses) {
        this.cClasses = cClasses;
    }

    public List<Team> getTeams() {
        return teams;
    }

    public void setTeams(List<Team> teams) {
        this.teams = teams;
    }

    public List<Student> getStudents() {
        return students;
    }

    public void setStudents(List<Student> students) {
        this.students = students;
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
                ", courseMemberLimitStrategy=" + courseMemberLimitStrategy +
                ", teacher=" + teacher +
                ", teamMainCourse=" + teamMainCourse +
                ", seminarMainCourse=" + seminarMainCourse +
                ", seminars=" + seminars +
                ", rounds=" + rounds +
                ", cClasses=" + cClasses +
                ", teams=" + teams +
                ", students=" + students +
                '}';
    }

    /**
     * Indicates whether some other object is "equal to" this one.
     * <p>
     * The {@code equals} method implements an equivalence relation
     * on non-null object references:
     * <ul>
     * <li>It is <i>reflexive</i>: for any non-null reference value
     * {@code x}, {@code x.equals(x)} should return
     * {@code true}.
     * <li>It is <i>symmetric</i>: for any non-null reference values
     * {@code x} and {@code y}, {@code x.equals(y)}
     * should return {@code true} if and only if
     * {@code y.equals(x)} returns {@code true}.
     * <li>It is <i>transitive</i>: for any non-null reference values
     * {@code x}, {@code y}, and {@code z}, if
     * {@code x.equals(y)} returns {@code true} and
     * {@code y.equals(z)} returns {@code true}, then
     * {@code x.equals(z)} should return {@code true}.
     * <li>It is <i>consistent</i>: for any non-null reference values
     * {@code x} and {@code y}, multiple invocations of
     * {@code x.equals(y)} consistently return {@code true}
     * or consistently return {@code false}, provided no
     * information used in {@code equals} comparisons on the
     * objects is modified.
     * <li>For any non-null reference value {@code x},
     * {@code x.equals(null)} should return {@code false}.
     * </ul>
     * <p>
     * The {@code equals} method for class {@code Object} implements
     * the most discriminating possible equivalence relation on objects;
     * that is, for any non-null reference values {@code x} and
     * {@code y}, this method returns {@code true} if and only
     * if {@code x} and {@code y} refer to the same object
     * ({@code x == y} has the value {@code true}).
     * <p>
     * Note that it is generally necessary to override the {@code hashCode}
     * method whenever this method is overridden, so as to maintain the
     * general contract for the {@code hashCode} method, which states
     * that equal objects must have equal hash codes.
     *
     * @param obj the reference object with which to compare.
     * @return {@code true} if this object is the same as the obj
     * argument; {@code false} otherwise.
     * @see #hashCode()
     * @see HashMap
     */

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        return ((Course) obj).id == this.id ? true : false;
    }
}
