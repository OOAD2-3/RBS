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
        //内容以html格式发送,防止被当成垃圾邮件
        StringBuffer messageText=new StringBuffer();
        messageText.append("<h2>OOAD课程通知</h2></br>");
        messageText.append("<a>这下应该是不在垃圾箱里，也不会被拒绝了</a>");
        //发送邮件抄送一份给自己，防止被认为是垃圾邮件
        boolean isSend = EmailUtils.sendEmail("ooadEmail邮件", new String[]{"1520166121@qq.com"},  new String[]{"ooadmail2_3@126.com"}, messageText.toString(), null);
        return "发送邮件:" + isSend;
    }
}
