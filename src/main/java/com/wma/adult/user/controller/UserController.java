package com.wma.adult.user.controller;

import com.wma.adult.base.ResponseModule;
import com.wma.adult.user.impl.UserRepository;
import com.wma.adult.user.module.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

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
    public ResponseModule saveUser(@RequestParam("account")String account, @RequestParam("password")String password) {
        System.out.println("saveUser");
        User user = new User();
        user.setAccount(account);
        user.setPassword(password);
        userRepository.save(user);
        Example<User> example = Example.of(user);
        Optional<User> result = userRepository.findOne(example);
        ResponseModule module = new ResponseModule(200,"保存成功",result.get());
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
