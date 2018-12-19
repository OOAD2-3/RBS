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
    private boolean ShareTeam;
    private boolean ShareSeminar;
    private String name;

    public CourseInfoVO(Course course) {
        id = course.getId();
        ShareTeam = true;
        ShareSeminar = true;
        name = course.getName();
        if (course.getTeamMainCourseId() == 0) {
            ShareTeam = false;
        }
        if (course.getSeminarMainCourseId() == 0) {
            ShareSeminar = false;
        }
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public boolean isShareTeam() {
        return ShareTeam;
    }

    public void setShareTeam(boolean shareTeam) {
        ShareTeam = shareTeam;
    }

    public boolean isShareSeminar() {
        return ShareSeminar;
    }

    public void setShareSeminar(boolean shareSeminar) {
        ShareSeminar = shareSeminar;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
