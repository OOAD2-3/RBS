package com.rbs.project.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.rbs.project.utils.EmailUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * @Author: WinstonDeng
 * @Description: OOAD_Course_ManageSystem
 * @Date: Created in 12:24 2018/12/16
 * @Modified by:
 */
@Service
public class EmailDemo {
    private static final Logger logger = LoggerFactory.getLogger(EmailDemo.class);

    public String sendEmail() throws JsonProcessingException {
        boolean isSend = EmailUtils.sendEmail("ooadEmail邮件测试", new String[]{"842237857@qq.com"}, null, "<h3><a>6不6，舒服了</a></h3>", null);
        return "发送邮件:" + isSend;
    }
}
