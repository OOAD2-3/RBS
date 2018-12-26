package com.rbs.project.service;

import com.rbs.project.dao.QuestionDao;
import com.rbs.project.pojo.entity.Question;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

}
