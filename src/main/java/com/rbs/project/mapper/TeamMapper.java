package com.rbs.project.mapper;

import com.rbs.project.pojo.entity.Team;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author: WinstonDeng
 * @Description: OOAD_Course_ManageSystem
 * @Date: Created in 14:10 2018/12/16
 * @Modified by:
 */
@Mapper
@Repository
public interface TeamMapper {
    //=================查找===================

    /**
     * 通过id查找队伍
     *
     * @param id
     * @return
     */
    Team findById(Long id);

    /**
     * 通过班级查找队伍列表
     *
     * @param cClassId
     * @return
     */
    List<Team> findByCClassId(Long cClassId);

    /**
     * 新建一个队伍
     *
     * @param team
     * @return
     */
    boolean insertTeam(Team team) throws Exception;
}
