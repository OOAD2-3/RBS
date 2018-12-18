package com.rbs.project.pojo.vo;

import com.rbs.project.pojo.entity.Course;

/**
 * Description:
 *
 * @Author: 17Wang
 * @Date: 21:59 2018/12/18
 */
public class CourseInfoVO {
    private long id;
    private boolean isShareTeam;
    private boolean isShareSeminar;
    private String name;

    public CourseInfoVO(Course course) {
        id = course.getId();
        isShareTeam = true;
        isShareSeminar = true;
        name = course.getName();
        if (course.getTeamMainCourseId() == 0) {
            isShareTeam = false;
        }
        if (course.getSeminarMainCourseId() == 0) {
            isShareSeminar = false;
        }
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public boolean isShareTeam() {
        return isShareTeam;
    }

    public void setShareTeam(boolean shareTeam) {
        isShareTeam = shareTeam;
    }

    public boolean isShareSeminar() {
        return isShareSeminar;
    }

    public void setShareSeminar(boolean shareSeminar) {
        isShareSeminar = shareSeminar;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
