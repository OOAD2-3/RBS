package com.rbs.project.pojo.vo;

import com.rbs.project.pojo.entity.Student;
import com.rbs.project.pojo.entity.Teacher;
import com.rbs.project.pojo.entity.User;

/**
 * Description:
 *
 * @Author: 17Wang
 * @Date: 16:29 2018/12/16
 */
public class UserVO {
    private long id;
    private String account;
    private String name;
    private String email;

    public UserVO(User user){
        id=user.getId();
        account=user.getUsername();
        email=user.getEmail();
        if(user instanceof Student){
            name=((Student) user).getStudentName();
        }else if(user instanceof Teacher){
            name=((Teacher) user).getTeacherName();
        }
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
