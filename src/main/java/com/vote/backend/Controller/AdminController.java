package com.vote.backend.Controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.vote.backend.Common.Result.CommonResult;
import com.vote.backend.Mapper.ChannelMapper;
import com.vote.backend.Mapper.OptionsMapper;
import com.vote.backend.Model.Channel;
import com.vote.backend.Model.Options;
import com.vote.backend.Model.Param.OptionParam;
import com.vote.backend.Model.Param.UserParam;
import com.vote.backend.Model.Param.VoteParam;
import com.vote.backend.Model.User;
import com.vote.backend.Model.VoteItem;
import com.vote.backend.Service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@RestController
@RequestMapping("/admin")
@PreAuthorize("hasAuthority('admin')")
public class AdminController {
    @Autowired
    AdminService adminService;


    @Autowired
    OptionsMapper optionsMapper;

   @PostMapping("/register")
   @JsonView(User.UserSimpleView.class)
    public CommonResult register(@RequestBody UserParam userParam){
       if(!userParam.getPassword().equals(userParam.getRepeat())){
           return CommonResult.Failed("两次输入密码不一致",null);
       }

       User user =adminService.registerAdmin(userParam);
       if(Objects.isNull(user)){
           return  CommonResult.Failed("注册失败,用户名已存在",null);
       }
       return  CommonResult.Success("注册成功",user);
   }


   @GetMapping ("/createChannel")
   public CommonResult createChannel(@RequestParam(value = "name")String name){
       return adminService.creatChannel(name);
   }
   @GetMapping("/deleteChannel")
   public CommonResult deleteChannel(@RequestParam(value = "name")String name){
       return adminService.deleteChannel(name);
   }


    @PostMapping("/createVote")
    public CommonResult createVote(@RequestBody VoteParam voteParam){
       return adminService.creatVote(voteParam);
   }
    @GetMapping("/deleteVote")
    public CommonResult deleteVote(@RequestParam(value = "id")Integer id){
        return adminService.deleteVote(id);
    }
    @GetMapping ("/pauseVote")
    public CommonResult pauseVote(@RequestParam (value = "id")Integer id){
       return adminService.pauseVote(id);
    }


    @PostMapping("/createOption")
    public CommonResult createOption(@RequestBody OptionParam optionParam){
       return adminService.creatOption(optionParam);
    }
    @PostMapping("/deleteOption")
    public CommonResult deleteOption(@RequestBody OptionParam optionParam){
        return adminService.deleteOption(optionParam);
    }








   @GetMapping("/test")
    public String test(){
       return "You are an admin";
   }


}
