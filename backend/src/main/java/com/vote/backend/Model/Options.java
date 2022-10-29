package com.vote.backend.Model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Options {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private String name;
    private Integer voteItemId;
    private Integer voteCount = 0;
}
