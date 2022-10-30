package com.vote.backend;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.vote.backend.Common.Utils.JwtUtil;
import com.vote.backend.Common.Utils.RedisCache;
import com.vote.backend.Mapper.OptionsMapper;
import com.vote.backend.Mapper.RecordMapper;
import com.vote.backend.Mapper.UserMapper;
import com.vote.backend.Mapper.VoteItemMapper;
import com.vote.backend.Model.Options;
import com.vote.backend.Model.Param.UserParam;
import com.vote.backend.Model.Record;
import com.vote.backend.Model.User;
import com.vote.backend.Model.VoteItem;
import com.vote.backend.Service.AdminService;
import com.vote.backend.Service.Imp.UserDetailServiceImp;
import com.vote.backend.Service.UsrAdminService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Objects;

@SpringBootTest
class BackendApplicationTests {
    @Autowired
    JwtUtil jwtUtil;
    @Autowired
    UserMapper userMapper;
    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    UsrAdminService usrAdminService;
    @Autowired
    UserDetailServiceImp userDetailServiceImp;
    @Autowired
    RedisCache redisCache;
    @Autowired
    VoteItemMapper voteItemMapper;
    @Autowired
    OptionsMapper optionsMapper;

    @Autowired
    AdminService adminService;
    @Autowired
    RecordMapper recordMapper;

    @Test
    public void selectUserByName(){

    }


}
