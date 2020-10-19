package com.wma.adult.controller;

import com.wma.adult.base.ResponseModule;
import com.wma.adult.impl.UserRepository;
import com.wma.adult.module.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {


    @Autowired
    public UserRepository userRepository;

    @GetMapping("/getUsers")
    public ResponseModule getUsers() {
        List<User> all = userRepository.findAll();
        ResponseModule module = new ResponseModule(200,"查询成功",all);
        return module;
    }


    @PostMapping("/saveUser")
    public ResponseModule saveUser(@RequestParam("name")String name, @RequestParam("phone")String phone) {
        System.out.println("saveUser");
        User user = new User();
        user.setName(name);
        user.setPhone(phone);
        userRepository.save(user);
        ResponseModule module = new ResponseModule(200,"保存成功",user);
        return module;
    }

    @GetMapping("/getUserById")
    public ResponseModule getUserById(@RequestParam("id") int id) {
        System.out.println("id = " + id);
        User user = userRepository.findById(id).get();
        ResponseModule module = new ResponseModule(200,"查询成功",user);
        return module;
    }

}
