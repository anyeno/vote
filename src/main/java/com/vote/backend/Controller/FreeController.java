package com.vote.backend.Controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fasterxml.jackson.annotation.JsonView;
import com.vote.backend.Mapper.ChannelMapper;
import com.vote.backend.Mapper.OptionsMapper;
import com.vote.backend.Mapper.VoteItemMapper;
import com.vote.backend.Model.Options;
import com.vote.backend.Model.Param.UserParam;
import com.vote.backend.Model.User;
import com.vote.backend.Model.VoteItem;
import com.vote.backend.Common.Result.CommonResult;
import com.vote.backend.Service.UsrAdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@RestController
@RequestMapping("/free")
public class FreeController {

    @Autowired
    UsrAdminService usrAdminService;

    //避免id和密码返回
    @JsonView(User.UserSimpleView.class)
   @PostMapping("/register")
   public CommonResult register(@RequestBody UserParam userParam) {
        if(!userParam.getPassword().equals(userParam.getRepeat())){
            return CommonResult.Failed("两次输入密码不一致",null);
        }

       User user =usrAdminService.registerUser(userParam);
       if(Objects.isNull(user)){
           return  CommonResult.Failed("注册失败,用户名已存在",null);
       }
       return  CommonResult.Success("注册成功",user);
   }

   @JsonView(User.UserSimpleView.class)
   @PostMapping("/login")
   public CommonResult login(@RequestBody UserParam user){

        return usrAdminService.login(user);
   }

    @Autowired
    VoteItemMapper voteItemMapper;

    @Autowired
    ChannelMapper channelMapper;

    @Autowired
    OptionsMapper optionsMapper;


/**资源获取*/
    @RequestMapping("/getAllChannel")//泛型类作为函数返回不用表明返回类型
    public CommonResult getAllChannels(){
        return CommonResult.Success("获取投票频道列表成功",channelMapper.selectList(null));
    }

    @RequestMapping("/getAllVote")
    public CommonResult getAlllVotes(@RequestParam(value = "id")int id){
     QueryWrapper<VoteItem> queryWrapper=new QueryWrapper<>();
     queryWrapper.eq("channel_id",id);//以数据库内数据项名为准
        return CommonResult.Success("获取投票实体列表成功", voteItemMapper.selectList(queryWrapper));
    }

    @RequestMapping(value = "/getAllOption",method = RequestMethod.GET)//默认即为get
    public CommonResult getAllOptions(@RequestParam(value = "channel_id")int channel_id
            , @RequestParam(value = "vote_id")int vote_id) {
        QueryWrapper<Options> optionsQueryWrapper = new QueryWrapper<>();
        optionsQueryWrapper.eq("channel_id", channel_id)//mole
                .eq("vote_item_id", vote_id);
        return CommonResult.Success("获取投票选项列表成功", optionsMapper.selectList(optionsQueryWrapper));
    }
//    @PostMapping("/register")
//    public

}
