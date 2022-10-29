package com.vote.backend.Service;

import com.vote.backend.Common.Result.CommonResult;
import com.vote.backend.Model.Param.UserParam;
import com.vote.backend.Model.User;

public interface UsrAdminService {
    public User registerUser(UserParam user);

    public CommonResult login(UserParam user);

    public CommonResult logout();

    public User selectUserByName(String name);
}
