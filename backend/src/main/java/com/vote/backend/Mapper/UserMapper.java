package com.vote.backend.Mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.vote.backend.Model.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper extends BaseMapper<User> {

}
