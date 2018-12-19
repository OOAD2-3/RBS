package com.rbs.project.pojo.strategy;

/**
 * @Author: WinstonDeng
 * @Description: course_member_limit_strategy 表记录的是
 *                 当一个队伍中有多门课程的同学时，对选修某门课程的学生人数的限制
 * @Date: Created in 14:47 2018/12/15
 * @Modified by:
 */
public class CourseMemberLimitStrategy {

    private long id;
    /**
     * 课程id
     */
    private long courseId;
    /**
     * 最少人数
     */
    private Integer minMember;
    /**
     * 最多人数
     */
    private Integer maxMember;

    //==================================================getter AND setter==================================================//

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getCourseId() {
        return courseId;
    }

    public void setCourseId(long courseId) {
        this.courseId = courseId;
    }

    public Integer getMinMember() {
        return minMember;
    }

    public void setMinMember(Integer minMember) {
        this.minMember = minMember;
    }

    public Integer getMaxMember() {
        return maxMember;
    }

    public void setMaxMember(Integer maxMember) {
        this.maxMember = maxMember;
    }

    @Override
    public String toString() {
        return "CourseMemberLimitStrategy{" +
                "id=" + id +
                ", courseId=" + courseId +
                ", minMember=" + minMember +
                ", maxMember=" + maxMember +
                '}';
    }
}
