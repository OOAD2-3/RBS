package com.rbs.project.controller;

import com.rbs.project.dao.UserDao;
import com.rbs.project.exception.MyException;
import com.rbs.project.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Description: user资源接口
 *
 * @Author: 17Wang
 * @Date: 11:51 2018/12/16
 */
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/password")
    @ResponseBody
    public ResponseEntity<Boolean> getPassword(@RequestParam("account") String account) throws MyException {
        return ResponseEntity.ok().body(userService.getPassword(account));
    }

}
