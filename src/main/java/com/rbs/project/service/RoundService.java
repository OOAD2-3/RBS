package com.rbs.project.service;

import com.rbs.project.dao.RoundDao;
import com.rbs.project.exception.MyException;
import com.rbs.project.pojo.entity.Round;
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
}
