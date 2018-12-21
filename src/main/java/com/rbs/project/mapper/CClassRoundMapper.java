package com.rbs.project.mapper;

import com.rbs.project.pojo.relationship.CClassRound;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * @Author: WinstonDeng
 * @Description: OOAD_Course_ManageSystem
 * @Date: Created in 15:37 2018/12/20
 * @Modified by:
 */
@Repository
public interface CClassRoundMapper {
    /**
     * 通过班级和轮次查找班级轮次记录
     * @param cClassId
     * @param roundId
     * @return
     */
    CClassRound findByPrimaryKeys(@Param("cClassId") long cClassId,@Param("roundId") long roundId);

    /**
     * 新增班级轮次
     * @param cClassRound
     * @return
     */
    boolean insertCClassRound(CClassRound cClassRound);
}
