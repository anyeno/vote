package com.vote.backend.Service.Imp;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.vote.backend.Mapper.UserMapper;
import com.vote.backend.Model.LoginUser;
import com.vote.backend.Model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
@Service
public class UserDetailServiceImp implements UserDetailsService {
    @Autowired
    UserMapper userMapper;


    @Override
    public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {
        /**查询用户信息*/
        QueryWrapper<User>queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("name",name);
        User user=userMapper.selectOne(queryWrapper);
        //空判断
        if(Objects.isNull(user)){
            throw new UsernameNotFoundException("用户名或密码错误");//继承上层错误类型
        }
        /**查询用户权限*/
        List<String> permissionList=new ArrayList<>();
        permissionList.add("user");
        if(user.isAdmin()){
            permissionList.add("admin");
        }


        /**封装用户权限信息*/
        return new LoginUser(user,permissionList);
    }
}
