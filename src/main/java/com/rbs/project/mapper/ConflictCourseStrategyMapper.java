package com.rbs.project.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * Description:
 *
 * @Author: 17Wang
 * @Date: 23:40 2018/12/21
 */
@Mapper
@Repository
public interface ConflictCourseStrategyMapper {
    /**
     * 找到最大的id
     *
     * @return
     */
    long findMaxId();

    /**
     * 插入一条数据
     *
     * @param id
     * @param courseId
     * @return
     */
    boolean insertOneLine(@Param("id") long id, @Param("courseId") long courseId);

    /**
     * 删除有关该课程的所有冲突策略
     *
     * @param courseId
     * @return
     */
    boolean deleteByCourseId(long courseId) throws Exception;
}
