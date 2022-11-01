package com.vote.backend.Controller;

import com.vote.backend.Common.Result.CommonResult;
import com.vote.backend.Model.Param.OptionParam;
import com.vote.backend.Service.UsrAdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@PreAuthorize("hasAuthority('user')")
public class UserAdminController {
    @Autowired
    UsrAdminService usrAdminService;
    @GetMapping("/logout")
    public CommonResult logout(){
        return usrAdminService.logout();
    }

    @GetMapping("/vote")
    public CommonResult vote(@RequestHeader("token") String token,@RequestParam(value = "id")int id){
        return usrAdminService.vote(id,token);
    }
    @GetMapping ("/vote_back")
    public CommonResult vote_back(@RequestHeader("token") String token,@RequestParam(value = "id")int id){

        return usrAdminService.vote_back(id,token);
    }

}
