package com.rbs.project.service;

import com.rbs.project.dao.CClassSeminarDao;
import com.rbs.project.dao.QuestionDao;
import com.rbs.project.exception.MyException;
import com.rbs.project.pojo.entity.CClassSeminar;
import com.rbs.project.pojo.entity.Question;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: WinstonDeng
 * @Description: service层专门处理业务，增删改查前对输入判空
 * @Date: Created in 0:06 2018/12/19
 * @Modified by:
 */
@Service
public class CClassSeminarService {
    @Autowired
    private CClassSeminarDao cClassSeminarDao;

    @Autowired
    private QuestionDao questionDao;



    /**
     * Description: 修改班级讨论课
     * @Author: WinstonDeng
     * @Date: 0:06 2018/12/19
     */
    public boolean updateCClassSeminar(CClassSeminar cClassSeminar) throws MyException {
        //判空
        if((Long)cClassSeminar.getcClassId()==null){
            throw new MyException("cClassId不能为空",MyException.ERROR);
        }
        if((Long)cClassSeminar.getSeminarId()==null){
            throw new MyException("seminarId不能为空",MyException.ERROR);
        }
        int oldStatus=cClassSeminarDao.findCClassSeminarByCClassIdAndSeminarId(cClassSeminar.getcClassId(),cClassSeminar.getSeminarId()).getStatus();
        //如果修改的是status
        if(!cClassSeminar.getStatus().equals(oldStatus)){
            cClassSeminarDao.updateCClassSeminar(cClassSeminar);
            //此处应有websocket通知状态改变
        }else {
            cClassSeminarDao.updateCClassSeminar(cClassSeminar);
        }
        return true;
    }

    /**
     * Description: 查看班级讨论课
     * @Author: WinstonDeng
     * @Date: 17:47 2018/12/21
     */
    public CClassSeminar getCClassSeminar(long cClassId,long seminarId) throws MyException{
        if((Long)cClassId==null){
            throw new MyException("cClassId不能为空",MyException.ERROR);
        }
        if((Long)seminarId==null){
            throw new MyException("seminarId不能为空",MyException.ERROR);
        }
        return cClassSeminarDao.findCClassSeminarByCClassIdAndSeminarId(cClassId,seminarId);
    }

    /**
     * Description: 通过讨论课id查看班级讨论课列表
     * @Author: WinstonDeng
     * @Date: 19:44 2018/12/21
     */
    public List<CClassSeminar> listAllCClassSeminarsBySeminarId(long seminarId) throws MyException{
        return cClassSeminarDao.findBySeminarId(seminarId);
    }

    /**
     * Description: 查看班级讨论课下所有提问
     * @Author: WinstonDeng
     * @Date: 15:54 2018/12/22
     */
    public List<Question> listAllQuestionsByCClassIdAndSeminarId(long cClassId,long seminarId) throws MyException{
        CClassSeminar cClassSeminar=cClassSeminarDao.findCClassSeminarByCClassIdAndSeminarId(cClassId,seminarId);
        return questionDao.listAllQuestionsByCClassSemianr(cClassSeminar,QuestionDao.HAS_CCLASS_SEMINAR,QuestionDao.HAS_STUDENT,QuestionDao.HAS_TEAM);
    }

    /**
     * Description: 修改班级讨论课下的提问
     * @Author: WinstonDeng
     * @Date: 16:02 2018/12/22
     * @param question
     */
    public boolean updateQuestion(Question question)throws MyException{
        Question temp=questionDao.getById(question.getId());
        temp.setSelected(question.getSelected());
        temp.setScore(question.getScore());
        return questionDao.updateQuestion(temp);
    }
}
