package com.rbs.project.pojo.strategy;

import java.math.BigInteger;

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
    private BigInteger courseId;
    /**
     * 策略id
     */
    private BigInteger strategyId;
    /**
     * 组队策略实现类名称
     */
    private String strategyName;

    //==================================================getter AND setter==================================================//

    public BigInteger getCourseId() {
        return courseId;
    }

    public void setCourseId(BigInteger courseId) {
        this.courseId = courseId;
    }

    public BigInteger getStrategyId() {
        return strategyId;
    }

    public void setStrategyId(BigInteger strategyId) {
        this.strategyId = strategyId;
    }

    public String getStrategyName() {
        return strategyName;
    }

    public void setStrategyName(String strategyName) {
        this.strategyName = strategyName;
    }
}
