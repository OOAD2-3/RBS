package com.rbs.project.pojo.strategy;

/**
 * @Author: WinstonDeng
 * @Description: OOAD_Course_ManageSystem
 * @Date: Created in 15:30 2018/12/15
 * @Modified by:
 */
public class TeamStragety {
    /**
     * 课程id
     */
    private long courseId;
    /**
     * 策略id
     */
    private long strategyId;
    /**
     * 组队策略实现类名称
     */
    private String strategyName;

    //==================================================getter AND setter==================================================//

    public long getCourseId() {
        return courseId;
    }

    public void setCourseId(long courseId) {
        this.courseId = courseId;
    }

    public long getStrategyId() {
        return strategyId;
    }

    public void setStrategyId(long strategyId) {
        this.strategyId = strategyId;
    }

    public String getStrategyName() {
        return strategyName;
    }

    public void setStrategyName(String strategyName) {
        this.strategyName = strategyName;
    }
}
