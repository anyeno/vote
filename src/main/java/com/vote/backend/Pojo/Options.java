package com.vote.backend.Pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Options {
    private Integer id;
    private String name;
    private Integer voteItemId;
    private Integer voteCount = 0;
}
