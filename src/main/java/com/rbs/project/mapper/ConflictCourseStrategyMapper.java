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
     * 插入一条冲突课程策略
     *
     * @param course1Id
     * @param course2Id
     * @return
     * @throws Exception
     */
    boolean insertConflictCourseStrategy(@Param("a") long course1Id, @Param("b") long course2Id) throws Exception;

    /**
     * （要与getById2合起来使用）获取与courseId相冲突的课程id
     *
     * @param courseId
     * @return
     */
    List<Long> getById1(long courseId);

    /**
     * （要与getById1合起来使用）获取与courseId相冲突的课程id
     *
     * @param courseId
     * @return
     */
    List<Long> getById2(long courseId);

    /**
     * 获取
     * @param courseId
     * @return
     */
    List<Long> getIdByCourseId(long courseId);

    /**
     * 通过id删除一条策略
     * @param id
     * @return
     * @throws Exception
     */
    boolean deleteById(long id) throws Exception;
}
