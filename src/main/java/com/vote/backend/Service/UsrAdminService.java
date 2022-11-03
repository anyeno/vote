package com.vote.backend.Service;

import com.vote.backend.Common.Result.CommonResult;
import com.vote.backend.Model.Param.OptionParam;
import com.vote.backend.Model.Param.UserParam;
import com.vote.backend.Model.User;

import java.util.Map;

public interface UsrAdminService {
    public User registerUser(UserParam user);

    public CommonResult login(UserParam user);

    public CommonResult logout();

    public User selectUserByName(String name);

    User getInfo(String token);

    public CommonResult vote(int id, String token);

    public CommonResult vote_back(int id,String token);

}
