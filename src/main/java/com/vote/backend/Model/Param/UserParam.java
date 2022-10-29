package com.vote.backend.Model.Param;

import lombok.Data;

@Data
public class UserParam {
    private String username;
    private String password;
    private String repeat;
}
