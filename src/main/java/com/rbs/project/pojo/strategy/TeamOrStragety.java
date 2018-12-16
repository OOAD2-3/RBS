package com.rbs.project.pojo.strategy;

/**
 * @Author: WinstonDeng
 * @Description: “或”组队策略
 * @Date: Created in 15:21 2018/12/15
 * @Modified by:
 */
public class TeamOrStragety {
    private long id;
    /**
     *  “或”组队策略1实现类名称
     */
    private String strategyAName;
    /**
     * “或”组队策略1_id
     */
    private long strategyAId;
    /**
     *  “或”组队策略2实现类名称
     */
    private String strategyBName;
    /**
     * “或”组队策略2_id
     */
    private long strategyBId;

    //==================================================getter AND setter==================================================//

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getStrategyAName() {
        return strategyAName;
    }

    public void setStrategyAName(String strategyAName) {
        this.strategyAName = strategyAName;
    }

    public long getStrategyAId() {
        return strategyAId;
    }

    public void setStrategyAId(long strategyAId) {
        this.strategyAId = strategyAId;
    }

    public String getStrategyBName() {
        return strategyBName;
    }

    public void setStrategyBName(String strategyBName) {
        this.strategyBName = strategyBName;
    }

    public long getStrategyBId() {
        return strategyBId;
    }

    public void setStrategyBId(long strategyBId) {
        this.strategyBId = strategyBId;
    }
}
