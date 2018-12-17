package com.rbs.project.pojo.vo;

import com.rbs.project.pojo.entity.CClass;

/**
 * @Author: WinstonDeng
 * @Description: OOAD_Course_ManageSystem
 * @Date: Created in 11:10 2018/12/17
 * @Modified by:
 */
public class CClassInfoVO {
    /**
     * id
     */
    long id;
    /**
     * 年级
     */
    Integer grade;
    /**
     * 班级序号
     */
    Integer serial;
    /**
     * 上课时间
     */
    String time;
    /**
     * 上课教室
     */
    String classroom;
    /**
     * 学生名单
     */
    String fileName;

    public CClassInfoVO(CClass cClass){
        id=cClass.getId();
        grade=cClass.getGrade();
        serial=cClass.getSerial();
        time=cClass.getTime();
        classroom=cClass.getPlace();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getClassroom() {
        return classroom;
    }

    public void setClassroom(String classroom) {
        this.classroom = classroom;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
}
