package com.rbs.project.dao;

import com.rbs.project.exception.MyException;
import com.rbs.project.mapper.CourseMapper;
import com.rbs.project.mapper.RoundMapper;
import com.rbs.project.mapper.SeminarMapper;
import com.rbs.project.pojo.entity.Round;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author: WinstonDeng
 * @Description: OOAD_Course_ManageSystem
 * @Date: Created in 11:17 2018/12/20
 * @Modified by:
 */

@Repository
public class RoundDao {
    @Autowired
    private RoundMapper roundMapper;

    @Autowired
    private CourseMapper courseMapper;

    @Autowired
    private SeminarMapper seminarMapper;

    public final static int HAS_SEMINAR=1;

    private void hasSomethingFun(Round round,int ...hasSomething){
        for(int i:hasSomething){
            if(i== HAS_SEMINAR){
                round.setSeminars(seminarMapper.findByRoundId(round.getId()));
            }
        }
    }
    /**
     * Description: 讨论课下设置 新增轮次
     * @Author: WinstonDeng
     * @Date: 11:18 2018/12/20
     */
    public long addRound(Round round) throws MyException{
        long createRoundId = -1;
        if(courseMapper.findById(round.getCourseId())==null){
            throw new MyException("新增轮次错误！课程未找到",MyException.NOT_FOUND_ERROR);
        }
        try {
            roundMapper.insertRound(round);
            createRoundId=round.getId();
        }catch (Exception e){
            System.out.println(e.getCause()+" "+e.getMessage());
            throw new MyException("新增轮次错误！数据库处理错误",MyException.ERROR);
        }
        return createRoundId;
    }

    /**
     * Description: 通过课程id查找轮次列表
     * @Author: WinstonDeng
     * @Date: 16:01 2018/12/20
     */
    public List<Round> listByCourseId(long courseId,int ...hasSomething) throws MyException{
        if((Long)courseId==null){
            throw new MyException("courseId不能为空",MyException.ERROR);
        }
        if(courseMapper.findById(courseId)==null){
            throw new MyException("查看轮次错误！课程不存在",MyException.NOT_FOUND_ERROR);
        }
        List<Round> rounds=null;
        try {
            rounds=roundMapper.findByCourseId(courseId);
        }catch (Exception e){
            throw new MyException("查看轮次错误！数据库处理错误",MyException.ERROR);
        }
        for(Round round
                :rounds){
            hasSomethingFun(round,hasSomething);
        }
        return rounds;
    }

    /**
     * Description: 通过id查找轮次
     * @Author: WinstonDeng
     * @Date: 19:21 2018/12/21
     */
    public Round findById(long roundId) throws MyException {
        Round round=null;
        try {
            round=roundMapper.findById(roundId);
        }catch (Exception e){
            throw new MyException("查找轮次错误！数据库处理错误",MyException.ERROR);
        }
        if(round==null){
            throw new MyException("查找轮次错误！轮次不存在",MyException.NOT_FOUND_ERROR);
        }
        return round;
    }
}
