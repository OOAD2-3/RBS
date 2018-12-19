package com.rbs.project.mapper;

import com.rbs.project.pojo.strategy.CourseMemberLimitStrategy;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * Description:
 *
 * @Author: 17Wang
 * @Date: 19:47 2018/12/18
 */
@Mapper
@Repository
public interface CourseMemberLimitStrategyMapper {
    /**
     * 新增策略
     *
     * @param courseMemberLimitStrategy
     * @return
     */
    boolean insertStrategy(CourseMemberLimitStrategy courseMemberLimitStrategy) throws Exception;

    /**
     * 通过课程id锁定一个策略
     *
     * @param courseId
     * @return
     */
    CourseMemberLimitStrategy getByCourseId(long courseId);

    /**
     * 通过课程id删除一个策略
     *
     * @param courseId
     * @return
     */
    boolean deleteByCourseId(long courseId);
}