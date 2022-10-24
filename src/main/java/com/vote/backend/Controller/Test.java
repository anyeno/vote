package com.vote.backend.Controller;

import com.vote.backend.Mapper.*;
import com.vote.backend.Pojo.Channel;
import com.vote.backend.Pojo.Options;
import com.vote.backend.Pojo.VoteItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
public class Test {
    @Autowired
    UserMapper userMapper;

    @Autowired
    RecordMapper recordMapper;

    @Autowired
    OptionsMapper optionsMapper;
    @RequestMapping("/options/")
    public List<Options> getOptions() {
        return optionsMapper.selectList(null);
    }

    @Autowired
    VoteItemMapper voteItemMapper;
    @RequestMapping("/vote/")
    public List<VoteItem> getString() {
        return voteItemMapper.selectList(null);
    }

    @Autowired
    ChannelMapper channelMapper;
    @RequestMapping("/ch/")
    public List<Channel> getCha() {
        return channelMapper.selectList(null);
    }
}
