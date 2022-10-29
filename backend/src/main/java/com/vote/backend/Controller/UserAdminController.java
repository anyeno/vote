package com.vote.backend.Controller;

import com.vote.backend.Common.Result.CommonResult;
import com.vote.backend.Service.UsrAdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user/")
@PreAuthorize("hasAuthority('user')")
public class UserAdminController {
    @Autowired
    UsrAdminService usrAdminService;
    @GetMapping("/logout")
    public CommonResult logout(){
        return usrAdminService.logout();
    }

}
