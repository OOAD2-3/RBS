package com.rbs.project.pojo.entity;

import java.math.BigInteger;

/**
 * @Author: WinstonDeng
 * @Description: course_member_limit_strategy 表记录的是
 *                 当一个队伍中有多门课程的同学时，对选修某门课程的学生人数的限制
 * @Date: Created in 14:47 2018/12/15
 * @Modified by:
 */
public class CourseMemberLimitStrategy {

    private BigInteger id;
    /**
     * 课程id
     */
    private BigInteger courseId;
    /**
     * 最少人数
     */
    private Integer minMember;
    /**
     * 最多人数
     */
    private Integer maxMember;

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
}
