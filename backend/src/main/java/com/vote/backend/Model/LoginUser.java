package com.vote.backend.Model;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Data
@NoArgsConstructor
public class LoginUser implements UserDetails {
    private User user;
    private List<String> permissionList;

    //不进行redis序列化
    @JSONField(serialize = false)
    private  List<SimpleGrantedAuthority>authorities;

    public LoginUser(User user,List<String>permission){
        this.user=user;
        this.permissionList=permission;


    }



    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getName();
    }

    public Integer getId(){
        return user.getId();
    }

    /**为了在jwtFilter中只有permissionList情况下生成authorities*/
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        //避免重复生成
        if(authorities!=null)return authorities;

        //进行权限类包装
        authorities=new ArrayList<>();
        for(String permisson:permissionList){
            authorities.add(new SimpleGrantedAuthority(permisson));
        }
        //函数式编程
        return authorities;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }


    //默认为true通过验证
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
