package com.rbs.project.pojo.strategy;

/**Bi
 * @Author: WinstonDeng
 * @Description: OOAD_Course_ManageSystem
 * @Date: Created in 15:25 2018/12/15
 * @Modified by:
 */
public class ConflictCourseStrategy {
    private long id;
    /**
     * 冲突课程1
     */
    private long courseAId;
    /**
     * 冲突课程2
     */
    private long courseBId;

    //==================================================getter AND setter==================================================//

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getCourseAId() {
        return courseAId;
    }

    public void setCourseAId(long courseAId) {
        this.courseAId = courseAId;
    }

    public long getCourseBId() {
        return courseBId;
    }

    public void setCourseBId(long courseBId) {
        this.courseBId = courseBId;
    }
}
