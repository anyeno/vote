package com.vote.backend.Mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.vote.backend.Pojo.Record;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface RecordMapper extends BaseMapper<Record> {
}
