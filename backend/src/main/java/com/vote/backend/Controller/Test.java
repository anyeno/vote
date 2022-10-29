package com.vote.backend.Controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.vote.backend.Common.Result.CommonResult;
import com.vote.backend.Mapper.*;
import com.vote.backend.Model.Param.UserParam;
import com.vote.backend.Model.User;
import com.vote.backend.Service.UsrAdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;




@RestController
public class Test {
    @Autowired
    UserMapper userMapper;

    @Autowired
    RecordMapper recordMapper;

    @Autowired
    OptionsMapper optionsMapper;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    UsrAdminService usrAdminService;

    @GetMapping("/hello")
    @PreAuthorize("hasAuthority('user')")
    public String hello(){
        return "hello_111";
    }

////    @PostMapping("/inputTest")
//    public List<User> input(@RequestBody User user){
//        User get=new User();
//        get.setName(user.getName());
//        get.setPassword(user.getPassword());
//
//    }




}

