package com.rbs.project.dao;

import com.rbs.project.exception.MyException;
import com.rbs.project.mapper.RoundScoreMapper;
import com.rbs.project.pojo.entity.RoundScore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * Description:
 *
 * @Author: 17Wang
 * @Date: 21:36 2018/12/21
 */
@Repository
public class RoundScoreDao {
    @Autowired
    private RoundScoreMapper roundScoreMapper;

    /**
     * Description: 新增轮次分数
     *
     * @Author: 17Wang
     * @Time: 21:39 2018/12/21
     */
    public boolean addRoundScore(RoundScore roundScore) throws Exception {
        if (!roundScoreMapper.insertRoundScore(roundScore)) {
            throw new MyException("新增轮次分数失败！数据库处理错误", MyException.ERROR);
        }
        return true;
    }

    /**
     * Description: 删除轮次分数
     * @Author: 17Wang
     * @Time: 23:12 2018/12/21
     */
    public boolean deleteRoundScoreByPrimaryKey(long roundId, long teamId) throws Exception {
        if (!roundScoreMapper.deleteByPrimaryKey(roundId, teamId)) {
            throw new MyException("删除轮次分数失败！数据库处理错误", MyException.ERROR);
        }
        return true;
    }
}
