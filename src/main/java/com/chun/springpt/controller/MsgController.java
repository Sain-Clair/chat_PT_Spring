package com.chun.springpt.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.chun.springpt.service.MsgService;
import com.chun.springpt.vo.MsgRoomVO;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/pt_chatroom")
public class MsgController {

    private final MsgService msgService;

    @PostMapping
    public MsgRoomVO createRoom(@RequestParam int name) {
        System.out.println("!!!!채팅방생성!!!!!" + name);
        return msgService.createRoom(name);
    }

    @GetMapping
    public List<MsgRoomVO> findAllRoom() {
    	System.out.println("!!!!리스트불러오기!!!!" );
        return msgService.findAllRoom();
    }
}