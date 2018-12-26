package com.rbs.project.mapper.strategy;

import com.rbs.project.pojo.strategy.TeamOrStrategy;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * Description:
 *
 * @Author: 17Wang
 * @Date: 23:42 2018/12/26
 */
@Mapper
@Repository
public interface TeamOrStrategyMapper {
    /**
     * 通过id获取策略信息
     *
     * @param id
     * @return
     */
    TeamOrStrategy findById(long id);
}
