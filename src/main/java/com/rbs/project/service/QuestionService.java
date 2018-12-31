package com.rbs.project.service;

import com.rbs.project.dao.QuestionDao;
import com.rbs.project.exception.MyException;
import com.rbs.project.pojo.entity.Question;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Description:
 *
 * @Author: 17Wang
 * @Date: 15:35 2018/12/26
 */
@Service
public class QuestionService {
    @Autowired
    private QuestionDao questionDao;

    /**
     * Description: 新增一个分数
     *
     * @Author: 17Wang
     * @Time: 9:19 2018/12/29
     */
    public boolean addQuestion(Question question) throws MyException {
        return questionDao.addQuestion(question);
    }

    /**
     * Description:
     * @Author: 17Wang
     * @Time: 12:53 2018/12/29
    */
    public List<Question> getQuestionBycClassSeminarId(long cClassSemianrId){
        return questionDao.getByCClassSeminarId(cClassSemianrId);
    }

}
