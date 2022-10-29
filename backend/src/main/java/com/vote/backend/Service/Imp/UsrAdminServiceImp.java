package com.vote.backend.Service.Imp;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.vote.backend.Common.Result.CommonResult;
import com.vote.backend.Common.Utils.JwtUtil;
import com.vote.backend.Common.Utils.RedisCache;
import com.vote.backend.Mapper.UserMapper;
import com.vote.backend.Model.LoginUser;
import com.vote.backend.Model.Param.UserParam;
import com.vote.backend.Model.User;
import com.vote.backend.Service.UsrAdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Service
public class UsrAdminServiceImp implements UsrAdminService {
    @Autowired
    UserMapper userMapper;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    JwtUtil jwtUtil;
    @Autowired
    RedisCache redisCache;


//
    @Override
    public User registerUser(UserParam p) {

        User user=new User(p);
        User res;

        //检查有无重名
            if(!Objects.isNull(selectUserByName(user.getName()))){
                return null;
            }
        //设置权限-密码加密
            user.setAdmin(false);
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            userMapper.insert(user);

            res=selectUserByName(user.getName());
        return res;
    }


    public CommonResult login( UserParam user){


      //进行封装-传入认证
        /**
         * 将authentication中的用户名自动传入UserDetailService(自动获取导入的Bean）
         * 将UserDetail(LoginUser)作为Authentication.Principal返回
         * */
        UsernamePasswordAuthenticationToken authenticationToken=new UsernamePasswordAuthenticationToken(
                user.getUsername(),user.getPassword());

        Authentication authentication;
        try {
            /**验证失败即错误跳出*/
             authentication = authenticationManager.authenticate(authenticationToken);
        }
        catch (Exception e){
            return CommonResult.Failed("登陆失败",null);
        }
        LoginUser loginUser=(LoginUser)authentication.getPrincipal();
        String id =loginUser.getId().toString();

        //生成jwt,写入map
        String token=jwtUtil.generatToken(id);
        Map<String,String>map=new HashMap<>();
        map.put("token",token);
//        map.put("getpasswd",loginUser.getPassword());

        //以id为标识将loguser存入redis
        redisCache.setCacheObject("login:"+id,loginUser);

        return CommonResult.Success("登录成功",map);

    }

    @Override
    public CommonResult logout() {
        /**SecurityContent是以请求单位区分的储存/进程间通信*/
       //从SecurityContent获取loginUser-id
       UsernamePasswordAuthenticationToken authenticationToken=(UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        LoginUser user= (LoginUser) authenticationToken.getPrincipal();
        String id=user.getId().toString();

        //删除redis用代表登陆状态的储存
        redisCache.deleteObject("login:"+id);
        return CommonResult.Success("注销成功",null);

    }

    public User selectUserByName(String name){
        QueryWrapper<User>queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("name",name);
        return userMapper.selectOne(queryWrapper);
    }

}
