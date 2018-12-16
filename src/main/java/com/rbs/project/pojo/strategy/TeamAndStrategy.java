package com.rbs.project.pojo.strategy;

import java.math.BigInteger;

/**
 * @Author: WinstonDeng
 * @Description: “与”组队策略
 * @Date: Created in 15:18 2018/12/15
 * @Modified by:
 */
public class TeamAndStrategy {
    private BigInteger id;
    /**
     *  “与”组队策略1实现类名称
     */
    private String strategyAName;
    /**
     * “与”组队策略1_id
     */
    private BigInteger strategyAId;
    /**
     *  “与”组队策略2实现类名称
     */
    private String strategyBName;
    /**
     * “与”组队策略2_id
     */
    private BigInteger strategyBId;

    //==================================================getter AND setter==================================================//

    public BigInteger getId() {
        return id;
    }

    public void setId(BigInteger id) {
        this.id = id;
    }

    public String getStrategyAName() {
        return strategyAName;
    }

    public void setStrategyAName(String strategyAName) {
        this.strategyAName = strategyAName;
    }

    public BigInteger getStrategyAId() {
        return strategyAId;
    }

    public void setStrategyAId(BigInteger strategyAId) {
        this.strategyAId = strategyAId;
    }

    public String getStrategyBName() {
        return strategyBName;
    }

    public void setStrategyBName(String strategyBName) {
        this.strategyBName = strategyBName;
    }

    public BigInteger getStrategyBId() {
        return strategyBId;
    }

    public void setStrategyBId(BigInteger strategyBId) {
        this.strategyBId = strategyBId;
    }
}
