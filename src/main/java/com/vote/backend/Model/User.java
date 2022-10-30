package com.vote.backend.Model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonView;
import com.vote.backend.Common.Result.CommonResult;
import com.vote.backend.Model.Param.UserParam;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {


    @JsonView(UserDetailView.class)
    @TableId(type = IdType.AUTO)
    private Integer id;
    @JsonView(UserSimpleView.class)
    private String name;
    @JsonView(UserDetailView.class)
    private String password;
    @JsonView(UserSimpleView.class)
    private boolean isAdmin;
    /**id从Param到User都为空由数据库作为主键自动填充*/
    public User(UserParam p){
        this.setPassword(p.getPassword());
        this.setName(p.getUsername());
    }

    /**限定返回格式*/
    //从简单逐渐继承到复杂
    public interface UserSimpleView extends CommonResult.CommonResultView {};
    public interface UserDetailView extends UserSimpleView{};
}
