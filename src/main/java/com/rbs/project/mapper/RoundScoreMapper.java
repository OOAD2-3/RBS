package com.rbs.project.mapper;

import com.rbs.project.exception.MyException;
import com.rbs.project.pojo.entity.RoundScore;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Description:
 *
 * @Author: 17Wang
 * @Date: 21:37 2018/12/21
 */
@Mapper
@Repository
public interface RoundScoreMapper {

    /**
     * 获取一个轮次下所有小组的轮次成绩
     *
     * @param roundId
     * @return
     */
    List<RoundScore> findByRoundId(long roundId);

    /**
     * 新建一个队伍在一个轮次的RoundScore
     *
     * @param roundScore
     * @return
     * @throws Exception
     */
    boolean insertRoundScore(RoundScore roundScore) throws Exception;

    /**
     * 删除一个队伍在一个轮次里的RoundScore
     *
     * @param roundId
     * @param teamId
     * @return
     * @throws Exception
     */
    boolean deleteByPrimaryKey(@Param("roundId") long roundId, @Param("teamId") long teamId) throws Exception;
}
