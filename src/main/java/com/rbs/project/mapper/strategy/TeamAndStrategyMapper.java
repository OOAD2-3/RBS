package com.rbs.project.mapper.strategy;

import com.rbs.project.pojo.entity.Team;
import com.rbs.project.pojo.strategy.TeamAndStrategy;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Description:
 *
 * @Author: 17Wang
 * @Date: 23:41 2018/12/26
 */
@Mapper
@Repository
public interface TeamAndStrategyMapper {
    /**
     * 通过id获取策略信息
     *
     * @param id
     * @return
     */
    List<TeamAndStrategy> findById(long id);
}
