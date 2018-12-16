package com.rbs.project.pojo.strategy;

import java.math.BigInteger;

/**
 * @Author: WinstonDeng
 * @Description: OOAD_Course_ManageSystem
 * @Date: Created in 15:25 2018/12/15
 * @Modified by:
 */
public class ConflictCourseStrategy {
    private BigInteger id;
    /**
     * 冲突课程1
     */
    private BigInteger courseAId;
    /**
     * 冲突课程2
     */
    private BigInteger courseBId;

    //==================================================getter AND setter==================================================//

    public BigInteger getId() {
        return id;
    }

    public void setId(BigInteger id) {
        this.id = id;
    }

    public BigInteger getCourseAId() {
        return courseAId;
    }

    public void setCourseAId(BigInteger courseAId) {
        this.courseAId = courseAId;
    }

    public BigInteger getCourseBId() {
        return courseBId;
    }

    public void setCourseBId(BigInteger courseBId) {
        this.courseBId = courseBId;
    }
}
