package com.rbs.project.pojo.dto;

/**
 * @Author: WinstonDeng
 * @Description: 新建班级DTO
 * @Date: Created in 14:35 2018/12/16
 * @Modified by:
 */
public class CreateCClassDTO {
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
