package com.rbs.project.pojo.entity;

import java.math.BigInteger;

/**
 * @Author: WinstonDeng
 * @Description: OOAD_Course_ManageSystem
 * @Date: Created in 15:13 2018/12/15
 * @Modified by:
 */
public class ShareSeminarApplication {
    private BigInteger id;
    /**
     * 主课程id
     */
    private BigInteger mainCourseId;
    /**
     * 从课程id
     */
    private BigInteger subCourseId;
    /**
     * 从课程老师id
     */
    private BigInteger subCourseTeacherId;
    /**
     * 请求状态，同意1、不同意0、未处理null
     */
    private Integer status;

    //关系
    /**
     * 一个主课程
     */
    private Course mainCourse;
    /**
     * 一个从课程
     */
    private Course subCourse;
    /**
     * 一个从课程老师
     */
    private Teacher subCourseTeacher;

    //==================================================getter AND setter==================================================//

    public BigInteger getId() {
        return id;
    }

    public void setId(BigInteger id) {
        this.id = id;
    }

    public BigInteger getMainCourseId() {
        return mainCourseId;
    }

    public void setMainCourseId(BigInteger mainCourseId) {
        this.mainCourseId = mainCourseId;
    }

    public BigInteger getSubCourseId() {
        return subCourseId;
    }

    public void setSubCourseId(BigInteger subCourseId) {
        this.subCourseId = subCourseId;
    }

    public BigInteger getSubCourseTeacherId() {
        return subCourseTeacherId;
    }

    public void setSubCourseTeacherId(BigInteger subCourseTeacherId) {
        this.subCourseTeacherId = subCourseTeacherId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    //================= toString ========================

    @Override
    public String toString() {
        return "ShareSeminarApplication{" +
                "id=" + id +
                ", mainCourseId=" + mainCourseId +
                ", subCourseId=" + subCourseId +
                ", subCourseTeacherId=" + subCourseTeacherId +
                ", status=" + status +
                ", mainCourse=" + mainCourse +
                ", subCourse=" + subCourse +
                ", subCourseTeacher=" + subCourseTeacher +
                '}';
    }
}
