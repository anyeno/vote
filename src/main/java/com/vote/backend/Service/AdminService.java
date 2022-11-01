package com.vote.backend.Service;

import com.vote.backend.Common.Result.CommonResult;
import com.vote.backend.Model.Param.OptionParam;
import com.vote.backend.Model.Param.UserParam;
import com.vote.backend.Model.Param.VoteParam;
import com.vote.backend.Model.User;

public interface AdminService {
    public User registerAdmin(UserParam user);

    public CommonResult creatChannel(String name);

    public CommonResult deleteChannel(String name);

    public CommonResult creatVote(VoteParam voteParam);

    public CommonResult deleteVote(Integer id);

    public CommonResult pauseVote(Integer id);

    public CommonResult creatOption(OptionParam optionParam);

    public CommonResult deleteOption(OptionParam optionParam);
}
