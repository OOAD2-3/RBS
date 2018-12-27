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
    /**
     * 学生姓名
     */
    private String studentName;

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        return ((Student) obj).getId() == this.getId() ? true : false;
    }
}
