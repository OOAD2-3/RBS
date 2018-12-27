package com.rbs.project.service;

import com.rbs.project.dao.RoundDao;
import com.rbs.project.exception.MyException;
import com.rbs.project.pojo.entity.Round;
import com.rbs.project.pojo.relationship.CClassRound;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: WinstonDeng
 * @Description: OOAD_Course_ManageSystem
 * @Date: Created in 19:19 2018/12/21
 * @Modified by:
 */
@Service
public class RoundService {
    @Autowired
    private RoundDao roundDao;

    /**
     * Description: 通过id查找轮次
     * @Author: WinstonDeng
     * @Date: 19:20 2018/12/21
     */
    public Round getRoundById(long roundId) throws MyException{
        if((Long)roundId==null){
            throw new MyException("roundId不能为空",MyException.ERROR);
        }
        return roundDao.findById(roundId);
    }

    /**
     * Description: 通过课程查找轮次
     * @Author: WinstonDeng
     * @Date: 22:38 2018/12/21
     */
    public List<Round> listRoundsByCourseId(long courseId) throws MyException{
        if((Long)courseId==null){
            throw new MyException("courseId不能为空",MyException.NOT_FOUND_ERROR);
        }
        return roundDao.listByCourseId(courseId,RoundDao.HAS_SEMINAR);
    }

    /**
     * Description: 修改分数计算方法
     * @Author: WinstonDeng
     * @Date: 1:45 2018/12/26
     */
    public boolean updateScoreMethod(Round round) throws MyException{
        return roundDao.updateScoreMethod(round);
    }

    /**
     * Description: 修改班级轮次的报名数
     * @Author: WinstonDeng
     * @Date: 1:45 2018/12/26
     */
    public boolean updateEnrollNumber(CClassRound cClassRound) throws MyException{
        return roundDao.updateEnrollNumber(cClassRound);
    }

    /**
     * Description: 通过classId roundId获取班级轮次
     * @Author: WinstonDeng
     * @Date: 16:47 2018/12/27
     */
    public CClassRound getCClassRoundByPrimaryKeys(long cClassId, long roundId) throws MyException {
        return roundDao.findCClassRoundByPrimaryKeys(cClassId,roundId);
    }
}
