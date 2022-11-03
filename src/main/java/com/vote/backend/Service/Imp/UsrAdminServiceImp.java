package com.vote.backend.Service.Imp;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.vote.backend.Common.Result.CommonResult;
import com.vote.backend.Common.Utils.JwtUtil;
import com.vote.backend.Common.Utils.RedisCache;
import com.vote.backend.Mapper.*;
import com.vote.backend.Model.*;
import com.vote.backend.Model.Param.OptionParam;
import com.vote.backend.Model.Param.UserParam;
import com.vote.backend.Model.Param.VoteParam;
import com.vote.backend.Service.UsrAdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Service
public class UsrAdminServiceImp implements UsrAdminService {
    @Autowired
    UserMapper userMapper;
    @Autowired
    ChannelMapper channelMapper;
    @Autowired
    VoteItemMapper voteItemMapper;
    @Autowired
    OptionsMapper optionsMapper;
    @Autowired
    RecordMapper recordMapper;
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

    @Override
    public User getInfo(String token) {
        Map<String, String> res = new HashMap<>();
        int id = 0;
        try {
            id = Integer.parseInt(jwtUtil.getId(token));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return userMapper.selectById(id);
    }



    @Override
    public CommonResult vote(int pid,String token) {
        //选项是否存在
        Options options = optionsMapper.selectById(pid);
          if(Objects.isNull(options)){
              return CommonResult.Failed("该选项不存在",null);
          }
        //是否过期
        VoteItem voteItem=voteItemMapper.selectById(options.getVoteItemId());
        if(LocalDateTime.now().isAfter(voteItem.getEndTime())){
            return CommonResult.Failed("投票已过期","过期时间:"+voteItem.getEndTime().toString());
        }
        //是否暂停
        if(voteItem.isPaused()){
            return CommonResult.Failed("投票已暂停,目前无法投票",null);
        }
          //是否进行过投票
        Integer userid=null;
        try{
            //String转型
            userid=Integer.parseInt(jwtUtil.getId(token));
        }
        catch (Exception e){
        }

        Integer id=ensureVote(options.getVoteItemId(),userid);
        if(!Objects.isNull(id)){
            return CommonResult.Failed("用户已经在此投票中进行过投票");
        }
        Record record=new Record();
        record.setVoteId(options.getVoteItemId());
        record.setUserId(userid);
        recordMapper.insert(record);

        options.setVoteCount(options.getVoteCount()+1);
        optionsMapper.updateById(options);

        return CommonResult.Success("投票成功",null);
    }

    @Override
    public CommonResult vote_back(int pid,String token) {
        //选项是否存在
        Options options=optionsMapper.selectById(pid);
        if(Objects.isNull(options)){
            return CommonResult.Failed("该选项不存在",null);
        }
        //是否过期
        VoteItem voteItem=voteItemMapper.selectById(options.getVoteItemId());
        if(LocalDateTime.now().isAfter(voteItem.getEndTime())){
            return CommonResult.Failed("投票已过期","过期时间:"+voteItem.getEndTime().toString());
        }
        //是否暂停
        if(voteItem.isPaused()){
            return CommonResult.Failed("投票已暂停,目前无法投票",null);
        }
        //是否进行过投票
        Integer userid=null;
        try{
            //String转型
            userid=Integer.parseInt(jwtUtil.getId(token));
        }
        catch (Exception e){
        }

        Integer id=ensureVote(options.getVoteItemId(),userid);
        if(Objects.isNull(id)){
            return CommonResult.Failed("用户未在此投票中进行过投票");
        }
        recordMapper.deleteById(id);

        options.setVoteCount(options.getVoteCount()-1);
        optionsMapper.updateById(options);

        return CommonResult.Success("撤回投票成功",null);

    }

    public Integer ensureVote(Integer voteId,Integer userId){
        QueryWrapper<Record>queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("user_id",userId)
                .eq("vote_id",voteId);
       Record record= recordMapper.selectOne(queryWrapper);
       if(Objects.isNull(record)){
           return null;
       }
       return record.getId();
    }



    /**字段类转为Pojo方法集*/

    public User selectUserByName(String name){
        QueryWrapper<User> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("name",name);
        return userMapper.selectOne(queryWrapper);
    }
    public Channel selectChannelByName(String name){
        QueryWrapper<Channel> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("name",name);
        return channelMapper.selectOne(queryWrapper);
    }
    public VoteItem selectVoteItemByName(VoteParam voteParam){
        Channel channel=selectChannelByName(voteParam.getChannelName());
        if(Objects.isNull(channel)){
            return null;
        }
        QueryWrapper<VoteItem> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("name",voteParam.getVoteName())
                .eq("channel_id",channel.getId());
        return voteItemMapper.selectOne(queryWrapper);
    }

    public Options selectOptionsByName(OptionParam optionParam){
        VoteItem voteItem=selectVoteItemByName(new VoteParam(optionParam.getVoteName(),optionParam.getChannelName(),null));
        if(Objects.isNull(voteItem)){
            return null;
        }
        QueryWrapper<Options>queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("name",optionParam.getOptionName())
                .eq("channel_id",voteItem.getChannelId())
                .eq("vote_item_id",voteItem.getId());
        return optionsMapper.selectOne(queryWrapper);
    }

}
