package com.rbs.project.pojo.vo;

import com.rbs.project.pojo.entity.Course;

/**
 * Description:
 *
 * @Author: 17Wang
 * @Date: 21:59 2018/12/18
 */
public class CourseInfoVO {
    private Long id;
    private Boolean ShareTeam;
    private Boolean ShareSeminar;
    private String name;

    public CourseInfoVO(){

    }

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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean isShareTeam() {
        return ShareTeam;
    }

    public void setShareTeam(Boolean shareTeam) {
        ShareTeam = shareTeam;
    }

    public Boolean isShareSeminar() {
        return ShareSeminar;
    }

    public void setShareSeminar(Boolean shareSeminar) {
        ShareSeminar = shareSeminar;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
