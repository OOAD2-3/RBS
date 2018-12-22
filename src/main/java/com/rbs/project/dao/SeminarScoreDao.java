package com.rbs.project.dao;

import com.rbs.project.exception.MyException;
import com.rbs.project.mapper.CClassMapper;
import com.rbs.project.mapper.CClassSeminarMapper;
import com.rbs.project.mapper.SeminarScoreMapper;
import com.rbs.project.mapper.TeamMapper;
import com.rbs.project.pojo.entity.SeminarScore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Description:
 *
 * @Author: 17Wang
 * @Date: 16:11 2018/12/22
 */
@Repository
public class SeminarScoreDao {
    @Autowired
    private SeminarScoreMapper seminarScoreMapper;

    @Autowired
    private CClassSeminarMapper cClassSeminarMapper;

    @Autowired
    private TeamMapper teamMapper;

    public final static int HAS_TEAM = 0;

    private void hasSomethingFun(SeminarScore seminarScore, int... hasSomething) {
        for (int i : hasSomething) {
            if (i == HAS_TEAM) {
                seminarScore.setTeam(teamMapper.findById(seminarScore.getTeamId()));
            }
        }
    }

    /**
     * Description:获取一个班级下的一节讨论课的所有展示成绩
     *
     * @Author: 17Wang
     * @Time: 16:19 2018/12/22
     */
    public List<SeminarScore> listAllSeminarScoreBySeminarIdAndCClassId(long seminarId, long cClasId, int... hasSomething) {
        List<SeminarScore> seminarScores = seminarScoreMapper.listAllBySeminarIdAndCClassId(seminarId, cClasId);
        for (SeminarScore seminarScore : seminarScores) {
            hasSomethingFun(seminarScore, hasSomething);

        }
        return seminarScores;
    }

    /**
     * Description: 新增一次展示分数
     *
     * @Author: 17Wang
     * @Time: 21:53 2018/12/21
     */
    public boolean addSeminarScore(long cClassSeminarId, long teamId) throws Exception {
        if (cClassSeminarMapper.findById(cClassSeminarId) == null) {
            throw new MyException("新增一次展示分数错误！该班级的该讨论课不存在", MyException.NOT_FOUND_ERROR);
        }
        if (teamMapper.findById(teamId) == null) {
            throw new MyException("新增一次展示分数！该小组不存在", MyException.NOT_FOUND_ERROR);
        }
        return seminarScoreMapper.insertSeminarScore(cClassSeminarId, teamId);
    }

    /**
     * Description: 通过主键删除展示分数
     *
     * @Author: 17Wang
     * @Time: 22:49 2018/12/21
     */
    public boolean deleteSeminarScoreByPrimaryKey(long cClassSeminarId, long teamId) throws Exception {
        if (!seminarScoreMapper.deleteSeminarScoreByPrimaryKey(cClassSeminarId, teamId)) {
            throw new MyException("通过主键删除展示分数！数据库处理错误", MyException.ERROR);
        }
        return true;
    }
}
