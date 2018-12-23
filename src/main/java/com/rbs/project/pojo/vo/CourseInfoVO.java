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
    private CClassInfoVO cClassInfoVO;

    public CourseInfoVO() {

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
        if (course.getcClasses() != null) {
            cClassInfoVO = new CClassInfoVO(course.getcClasses().get(0));
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean getShareTeam() {
        return ShareTeam;
    }

    public void setShareTeam(Boolean shareTeam) {
        ShareTeam = shareTeam;
    }

    public Boolean getShareSeminar() {
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

    public CClassInfoVO getcClassInfoVO() {
        return cClassInfoVO;
    }

    public void setcClassInfoVO(CClassInfoVO cClassInfoVO) {
        this.cClassInfoVO = cClassInfoVO;
    }
}
