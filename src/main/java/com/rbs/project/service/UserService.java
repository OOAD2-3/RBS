package com.rbs.project.service;

import com.rbs.project.dao.UserDao;
import com.rbs.project.exception.MyException;
import com.rbs.project.pojo.entity.Student;
import com.rbs.project.pojo.entity.Teacher;
import com.rbs.project.pojo.entity.User;
import com.rbs.project.utils.EmailUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Description:
 *
 * @Author: 17Wang
 * @Date: 12:04 2018/12/16
 */
@Service
public class UserService {
    @Autowired
    private UserDao userDao;

    /**
     * Description: 激活用户，包括学生和老师
     *
     * @Author: 17Wang
     * @Time: 13:09 2018/12/16
     */
    public boolean userActivation(User user) throws MyException {
        //判断json格式，密码不能为空
        if (user.getPassword() == null) {
            throw new MyException("激活失败！密码不能为空", MyException.ERROR);
        }
        //判断json格式，邮箱不能为空
        if (user.getEmail() == null) {
            throw new MyException("激活失败！邮箱不能为空", MyException.ERROR);
        }
        if (user instanceof Student) {
            userDao.updatePasswordAndEmailAndActiveByStudent((Student) user);
        } else if (user instanceof Teacher) {
            userDao.updatePasswordAndEmailAndActiveByTeacher((Teacher) user);
        }

        return true;
    }

    /**
     * Description: 找回密码
     *
     * @Author: 17Wang
     * @Time: 14:33 2018/12/16
     */
    public boolean getPassword(String account) throws MyException {
        User user = userDao.getUserByUsername(account);
        if (user == null) {
            throw new MyException("找回密码失败！不存在该用户", MyException.NOT_FOUND_ERROR);
        }
        if(user.getEmail()==null || !EmailUtils.checkEmailFormat(user.getEmail())){
            throw new MyException("找回密码失败！邮件名错误", MyException.ERROR);
        }
        boolean isSend=EmailUtils.sendEmail("找回密码", new String[]{user.getEmail()}, null, "<p>你的密码是："+user.getPassword()+"</p>", null);
        if(!isSend){
            throw new MyException("找回密码失败！发送邮件错误", MyException.ERROR);
        }
        return isSend;
    }
}
