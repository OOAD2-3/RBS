package com.rbs.project.pojo.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Description:
 *
 * @Author: 17Wang
 * @Date: 15:20 2018/12/15
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class Student extends User{
    private int id;
    private String studentName;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }
}
