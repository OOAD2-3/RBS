package com.rbs.project.service;

import com.rbs.project.dao.CClassSeminarDao;
import com.rbs.project.exception.MyException;
import com.rbs.project.pojo.entity.CClassSeminar;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    /**
     * Description: 修改班级讨论课reportDDL
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
}
