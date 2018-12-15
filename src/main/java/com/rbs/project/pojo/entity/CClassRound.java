package com.rbs.project.pojo.entity;

import java.math.BigInteger;

/**
 * @Author: WinstonDeng
 * @Description: OOAD_Course_ManageSystem
 * @Date: Created in 15:27 2018/12/15
 * @Modified by:
 */
public class CClassRound {
    /**
     * 班级id
     */
    private BigInteger cClassId;
    /**
     * 轮次id
     */
    private BigInteger roundId;
    /**
     * 某班级，某轮次队伍报名次数限制
     */
    private Integer enrollNumber;

    //关系
    /**
     * 一个班级
     */
    private CClass cClass;
    /**
     * 一个轮次
     */
    private Round round;

    //==================================================getter AND setter==================================================//

    public BigInteger getcClassId() {
        return cClassId;
    }

    public void setcClassId(BigInteger cClassId) {
        this.cClassId = cClassId;
    }

    public BigInteger getRoundId() {
        return roundId;
    }

    public void setRoundId(BigInteger roundId) {
        this.roundId = roundId;
    }

    public Integer getEnrollNumber() {
        return enrollNumber;
    }

    public void setEnrollNumber(Integer enrollNumber) {
        this.enrollNumber = enrollNumber;
    }

    //================== toString ===========================


    @Override
    public String toString() {
        return "CClassRound{" +
                "cClassId=" + cClassId +
                ", roundId=" + roundId +
                ", enrollNumber=" + enrollNumber +
                ", cClass=" + cClass +
                ", round=" + round +
                '}';
    }
}
