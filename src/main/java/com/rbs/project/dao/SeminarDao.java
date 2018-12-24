package com.rbs.project.dao;

import com.rbs.project.exception.MyException;
import com.rbs.project.mapper.CClassSeminarMapper;
import com.rbs.project.mapper.CourseMapper;
import com.rbs.project.mapper.RoundMapper;
import com.rbs.project.mapper.SeminarMapper;
import com.rbs.project.pojo.entity.CClassSeminar;
import com.rbs.project.pojo.entity.Seminar;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: WinstonDeng
 * @Description: OOAD_Course_ManageSystem
 * @Date: Created in 11:44 2018/12/18
 * @Modified by:
 */
@Repository
public class SeminarDao {
    @Autowired
    private SeminarMapper seminarMapper;

    @Autowired
    private CourseMapper courseMapper;

    @Autowired
    private CClassSeminarMapper cClassSeminarMapper;

    @Autowired
    private RoundMapper roundMapper;

    public final static int HAS_CClASS_SEMINAR=0;
    public final static int HAS_ROUND=1;
    public final static int HAS_COURSE=2;

    private void hasSomethingFun(Seminar seminar,int ...hasSomething){
        for(int i:hasSomething){
            if(i==HAS_CClASS_SEMINAR){
                seminar.setcClassSeminars(cClassSeminarMapper.findBySeminarId(seminar.getId()));
            }
            if(i==HAS_ROUND){
                seminar.setRound(roundMapper.findById(seminar.getRoundId()));
            }
            if(i==HAS_COURSE){
                seminar.setCourse(courseMapper.findById(seminar.getCourseId()));
            }
        }
    }

    /**
     * Description: 新增讨论课
     * @Author: WinstonDeng
     * @Date: 15:46 2018/12/18
     */
    public boolean addSeminar(Seminar seminar)throws MyException {
        try {
             seminarMapper.insertSeminar(seminar);
        }catch (Exception e){
            throw new MyException("新建讨论课错误！数据库处理错误",MyException.ERROR);
        }
        return true;
    }

    /**
     * Description: 按id修改讨论课
     * @Author: WinstonDeng
     * @Date: 16:23 2018/12/18
     */
    public boolean updateSeminarById(Seminar seminar) throws MyException{
        if(seminarMapper.findById(seminar.getId())==null){
            throw new MyException("修改讨论课信息错误！未找到讨论课",MyException.NOT_FOUND_ERROR);
        }
        try {
            Seminar temp=seminarMapper.findById(seminar.getId());
            temp.setName(seminar.getName());
            temp.setIntro(seminar.getIntro());
            temp.setSerial(seminar.getSerial());
            temp.setVisible(seminar.getVisible());
            temp.setMaxTeam(seminar.getMaxTeam());
            temp.setRoundId(seminar.getRoundId());
            temp.setEnrollStartTime(seminar.getEnrollStartTime());
            temp.setEnrollEndTime(seminar.getEnrollEndTime());
            seminarMapper.updateSeminar(temp);
        }catch (Exception e){
            throw new MyException("修改讨论课错误！数据库处理错误",MyException.ERROR);
        }
        return true;
    }

    /**
     * Description: 按id删除讨论课
     * @Author: WinstonDeng
     * @Date: 16:24 2018/12/18
     */
    public boolean removeSeminarById(long seminarId) throws MyException{
        try {
            seminarMapper.removeSeminarById(seminarId);
        }catch (Exception e){
            throw new MyException("删除讨论课错误！数据库处理错误",MyException.ERROR);
        }
        return true;
    }

    /**
     * Description: 按seminarId删除班级讨论课
     * @Author: WinstonDeng
     * @Date: 21:26 2018/12/20
     */
    public boolean removeCClassSeminarBySeminarId(long seminarId) throws MyException{
        try {
            cClassSeminarMapper.removeCClassSeminarBySeminarId(seminarId);
        }catch (Exception e){
            throw new MyException("删除班级讨论课错误！数据库处理错误",MyException.ERROR);
        }
        return true;
    }

    /**
     * Description: 按id查找讨论课
     * @Author: WinstonDeng
     * @Date: 20:53 2018/12/20
     */
    public Seminar findSeminarById(long seminarId,int ...hasSomething) throws MyException{
        Seminar seminar=seminarMapper.findById(seminarId);
        if(seminar==null){
            throw new MyException("查看讨论课错误！未找到该记录",MyException.NOT_FOUND_ERROR);
        }
        hasSomethingFun(seminar,hasSomething);
        return seminar;
    }

    /**
     * Description: 通过courseId查看讨论课
     * @Author: WinstonDeng
     * @Date: 10:11 2018/12/21
     */
    public List<Seminar> findSeminarByCourseId(long courseId,int ...hasSomething) throws MyException{
        List<Seminar> seminars=seminarMapper.findByCourseId(courseId);
        for(Seminar seminar:seminars){
            hasSomethingFun(seminar,hasSomething);
        }
        return seminars;
    }

}
