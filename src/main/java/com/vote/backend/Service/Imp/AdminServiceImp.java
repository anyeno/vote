package com.vote.backend.Service.Imp;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.vote.backend.Common.Result.CommonResult;
import com.vote.backend.Mapper.ChannelMapper;
import com.vote.backend.Mapper.OptionsMapper;
import com.vote.backend.Mapper.UserMapper;
import com.vote.backend.Mapper.VoteItemMapper;
import com.vote.backend.Model.Channel;
import com.vote.backend.Model.Options;
import com.vote.backend.Model.Param.OptionParam;
import com.vote.backend.Model.Param.UserParam;
import com.vote.backend.Model.Param.VoteParam;
import com.vote.backend.Model.User;
import com.vote.backend.Model.VoteItem;
import com.vote.backend.Service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Objects;

@Component
public class AdminServiceImp implements AdminService {
    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    ChannelMapper channelMapper;
    @Autowired
    UserMapper userMapper;
    @Autowired
    VoteItemMapper voteItemMapper;
    @Autowired
    OptionsMapper optionsMapper;

    @Autowired
    UsrAdminServiceImp usrAdminServiceImp;

    @Override
    public User registerAdmin(UserParam p) {
        User user=new User(p);
        User res;

        //检查有无重名
        if(!Objects.isNull(usrAdminServiceImp.selectUserByName(user.getName()))){
            return null;
        }
        //设置权限-密码加密
        user.setAdmin(true);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userMapper.insert(user);

        res=usrAdminServiceImp.selectUserByName(user.getName());
        return res;
    }
    //频道操作
    @Override
    public CommonResult creatChannel(String name) {
        Channel channel=usrAdminServiceImp.selectChannelByName(name);
       if(!Objects.isNull(channel)){
           return CommonResult.Failed("频道已经存在");
       }
        Channel newChannel=new Channel();
       newChannel.setName(name);
        channelMapper.insert(newChannel);

        return CommonResult.Success("成功创建频道","频道名："+name);
    }
    @Override
    public CommonResult deleteChannel(String name) {
        Channel channel=usrAdminServiceImp.selectChannelByName(name);
        if(Objects.isNull(channel)){
            return CommonResult.Failed("频道不存在无需删除");
        }
        QueryWrapper<Channel>queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("id",channel.getId());
       channelMapper.delete(queryWrapper);

       return CommonResult.Success("删除频道成功",null);
    }


    @Override
    public CommonResult creatVote(VoteParam voteParam) {
        Channel channel= usrAdminServiceImp.selectChannelByName(voteParam.getChannelName());
        if(Objects.isNull(channel)){
            return CommonResult.Failed("频道不存在",null);
        }
        //查询是否存在和删除可以使用相同条件
        VoteItem voteItemif=usrAdminServiceImp.selectVoteItemByName(voteParam);
        if(!Objects.isNull(voteItemif)){
            return CommonResult.Failed("投票已经存在",null);
        }

        VoteItem voteItem=new VoteItem();
        voteItem.setName(voteParam.getVoteName());
        voteItem.setChannelId(channel.getId());

        voteItem.setCreateTime(LocalDateTime.now());
        voteItem.setEndTime(
                LocalDateTime.now().plusHours((long)voteParam.getDuring()));

        voteItem.setPaused(false);

        voteItemMapper.insert(voteItem);
        return CommonResult.Success("创建投票成功",voteItem);
    }



    @Override
    public CommonResult deleteVote(Integer id) {
        VoteItem voteItem = voteItemMapper.selectById(id);
        if(Objects.isNull(voteItem)){
            return CommonResult.Failed("投票不存在，无需删除",null);
        }
        voteItemMapper.deleteById(voteItem.getId());
        return CommonResult.Success("删除成功",null);

    }

    @Override
    public CommonResult pauseVote(Integer id) {

        VoteItem voteItem=voteItemMapper.selectById(id);
        if(Objects.isNull(voteItem)){
            return CommonResult.Failed("投票不存在",null);
        }
        voteItem.setPaused(!voteItem.isPaused());
        //直接放入即可更改
        voteItemMapper.updateById(voteItem);
        return CommonResult.Success("投票更新成功",voteItem);
    }


    @Override
    public CommonResult creatOption(OptionParam optionParam) {
        VoteItem vote=usrAdminServiceImp.selectVoteItemByName(new VoteParam(optionParam.getVoteName(),optionParam.getChannelName(),null));
        if(Objects.isNull(vote)){
            return CommonResult.Failed("投票实体不存在",null);
        }
        Options optionsif=usrAdminServiceImp.selectOptionsByName(optionParam);
        if(!Objects.isNull(optionsif)){
            return CommonResult.Failed("投票选项已存在",null);
        }
        Options options=new Options();
        options.setName(optionParam.getOptionName());
        options.setChannelId(vote.getChannelId());
        options.setVoteItemId(vote.getId());
        options.setVoteCount(0);

        optionsMapper.insert(options);
        return CommonResult.Success("选项创建成功",options);

    }

    @Override
    public CommonResult deleteOption(OptionParam optionParam) {
        Options options=usrAdminServiceImp.selectOptionsByName(optionParam);
        if(Objects.isNull(options)){
            return CommonResult.Failed("投票选项不存在",null);
        }
        optionsMapper.deleteById(options.getId());
        return CommonResult.Success("投票选项删除成功",null);

    }




}
