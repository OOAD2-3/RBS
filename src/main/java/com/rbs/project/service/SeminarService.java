package com.rbs.project.service;

import com.rbs.project.dao.CClassDao;
import com.rbs.project.dao.CClassSeminarDao;
import com.rbs.project.dao.RoundDao;
import com.rbs.project.dao.SeminarDao;
import com.rbs.project.exception.MyException;
import com.rbs.project.pojo.entity.CClass;
import com.rbs.project.pojo.entity.Round;
import com.rbs.project.pojo.entity.Seminar;
import com.rbs.project.pojo.relationship.CClassRound;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Author: WinstonDeng
 * @Description: OOAD_Course_ManageSystem
 *
 *              !!!!!!!!!!!!!!!!!!!!!!!!!!    增删改前先查出来，检查对应字段，防止脏数据，做到同步    !!!!!!!!!!!!!!!!!!!!!!!!!!!!
 *
 * @Date: Created in 11:46 2018/12/18
 * @Modified by:
 */
@Service
public class SeminarService {
    @Autowired
    private SeminarDao seminarDao;

    @Autowired
    private RoundDao roundDao;

    @Autowired
    private CClassDao cClassDao;

    @Autowired
    private CClassSeminarDao cClassSeminarDao;

    /**
     * Description: 新建讨论课
     * @Author: WinstonDeng
     * @Date: 16:25 2018/12/18
     */
    @Transactional(rollbackFor = Exception.class)
    public long addSemianr(Seminar seminar) throws MyException{
        //初始化新增讨论课id
        long createSeminarId=-1;
        //如果轮次为空，则新建一个轮次，级联修改
        //判断为null太傻吊。接进来数据null的set不进来，沙雕转换为-1，就不是空了，这里再判断，自己难为自己
       if(seminar.getRoundId() == -1){
            seminar=addRoundBussiness(seminar);
        }
        //其他判空
        if(seminar.getCourseId() ==- 1){
            throw new MyException("courseId不能为空",MyException.ERROR);
        }
        if(seminar.getName()==null){
            throw new MyException("name不能为空",MyException.ERROR);
        }
        if(seminar.getMaxTeam()==null){
            throw new MyException("maxTeam不能为空",MyException.ERROR);
        }
        if(seminar.getVisible()==null){
            throw new MyException("visible不能为空",MyException.ERROR);
        }
        if(seminar.getSerial()==null){
            throw new MyException("seria;不能为空",MyException.ERROR);
        }
        //判断序号是否存在
        List<Seminar> seminars=seminarDao.findSeminarByCourseId(seminar.getCourseId());
        for(Seminar temp
                :seminars){
            //若该序号已存在，则报错
            if(temp.getSerial().equals(seminar.getSerial())){
                throw new MyException("该讨论课序号已存在！",MyException.ERROR);
            }
        }
        //新增讨论课
        seminarDao.addSeminar(seminar);
        //新增班级讨论课
        cClassSeminarDao.addCClassSeminar(seminar);
        //获得主键
        createSeminarId=seminar.getId();
        return createSeminarId;
    }

    /**
     * Description: 按id修改讨论课
     * @Author: WinstonDeng
     * @Date: 16:29 2018/12/18
     */
    @Transactional(rollbackFor = Exception.class)
    public boolean updateSeminar(Seminar seminar)throws MyException{
        //如果轮次为空，则新建一个轮次，级联修改
        if((Long)seminar.getRoundId()==null){
            seminar=addRoundBussiness(seminar);
        }
        //其他判空
        if((Long)seminar.getCourseId()==null){
            throw new MyException("courseId不能为空",MyException.ERROR);
        }
        if(seminar.getName()==null){
            throw new MyException("name不能为空",MyException.ERROR);
        }
        if(seminar.getMaxTeam()==null){
            throw new MyException("maxTeam不能为空",MyException.ERROR);
        }
        if(seminar.getVisible()==null){
            throw new MyException("visible不能为空",MyException.ERROR);
        }
        if(seminar.getSerial()==null){
            throw new MyException("seria;不能为空",MyException.ERROR);
        }
        return seminarDao.updateSeminarById(seminar);
    }

    /**
     * Description: 按id删除讨论课
     * @Author: WinstonDeng
     * @Date: 16:29 2018/12/18
     */
    @Transactional(rollbackFor = Exception.class)
    public boolean removeSeminarById(long seminarId) throws MyException{
        if((Long)seminarId==null){
            throw new MyException("seminarId不能为空",MyException.ERROR);
        }
        //级联删除 班级讨论课
        seminarDao.removeCClassSeminarBySeminarId(seminarId);
        seminarDao.removeSeminarById(seminarId);
        return true;
    }

    /**
     * Description: 按id查找讨论课
     * @Author: WinstonDeng
     * @Date: 20:50 2018/12/20
     */
    public Seminar getSeminarById(long seminarId) throws MyException{
        if((Long)seminarId==null){
            throw new MyException("seminarId不能为空",MyException.ERROR);
        }
        return seminarDao.findSeminarById(seminarId);
    }
    /**
     * Description: 【私有】 新建轮次的业务逻辑方法
     * @Author: WinstonDeng
     * @Date: 16:10 2018/12/20
     */
    private Seminar addRoundBussiness(Seminar seminar)throws MyException{
        //新建一个轮次
        Round round=new Round();
        //查找当前课程下的所有轮次，确认轮次次序
        int roundNum=roundDao.findByCourseId(seminar.getCourseId()).size();
        //新增的轮次次序+1
        round.setSerial(roundNum+1);
        round.setCourseId(seminar.getCourseId());
        //设置其他默认值
        round.setPresentationScoreMethod(0);
        round.setReportScoreMethod(0);
        round.setQuestionScoreMethod(0);
        long roundId=roundDao.addRound(round);
        round.setId(roundId);
        //与新建的seminar建立关系
        seminar.setRoundId(round.getId());
        //对该课程下的所有班级，新建klass_round记录，并设置其中enrollNumber字段
        List<CClass> cClasses=cClassDao.listByCourseId(seminar.getCourseId());
        for(CClass cClass:cClasses){
            CClassRound cClassRound=new CClassRound();
            cClassRound.setcClassId(cClass.getId());
            cClassRound.setRoundId(seminar.getRoundId());
            //默认1次
            final int defaultEnrollNumber=1;
            cClassRound.setEnrollNumber(defaultEnrollNumber);
            cClassDao.addCClassRound(cClassRound);
        }
        return seminar;
    }
}