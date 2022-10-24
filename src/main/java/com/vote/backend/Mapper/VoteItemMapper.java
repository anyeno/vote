package com.vote.backend.Mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.vote.backend.Pojo.VoteItem;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface VoteItemMapper extends BaseMapper<VoteItem> {
}
