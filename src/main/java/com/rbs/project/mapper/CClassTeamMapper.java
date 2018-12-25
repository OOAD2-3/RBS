package com.rbs.project.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * Description:
 *
 * @Author: 17Wang
 * @Date: 21:58 2018/12/25
 */
@Mapper
@Repository
public interface CClassTeamMapper {

    /**
     * 新增一条数据
     * @param cClassId
     * @param teamId
     * @return
     * @throws Exception
     */
    boolean insertBycClassIdAndTeamId(@Param("cClassId") long cClassId,@Param("teamId") long teamId) throws Exception;
}
