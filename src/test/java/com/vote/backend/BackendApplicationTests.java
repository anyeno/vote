package com.vote.backend;

import com.vote.backend.Common.Utils.JwtUtil;
import com.vote.backend.Mapper.UserMapper;
import com.vote.backend.Model.Param.UserParam;
import com.vote.backend.Model.User;
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

    @Test
    public void testR(){
        System.out.println("start");
        UserParam userParam=new UserParam();
        userParam.setUsername("mole");
        userParam.setPassword("114514");
        User user=usrAdminService.registerUser(userParam);
        System.out.println(user);

    }

}
