package com.vote.backend.Model.Param;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VoteParam {
    String voteName;
    String channelName;
    Integer during;
}
