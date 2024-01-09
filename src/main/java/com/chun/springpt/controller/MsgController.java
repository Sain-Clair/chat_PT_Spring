package com.chun.springpt.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.chun.springpt.dao.MessageDao;
import com.chun.springpt.service.MsgService;
import com.chun.springpt.vo.MsgRoomVO;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/chat")
public class MsgController {

    private final MsgService msgService;
    
    @PostMapping
    public MsgRoomVO createRoom(@RequestParam String name) {
        System.out.println("!!!!채팅방생성!!!!!");
        return msgService.createRoom(name);
    	
//    	 int generatedRoomName = generateRoomName(); // MyBatis를 사용하여 방 번호 생성
//         System.out.println("!!!!채팅방생성!!!!!" + generatedRoomName);
//         MessageDao.insertRoom(generatedRoomName); // 방 생성 쿼리 실행
//         MsgRoomVO roomVO = new MsgRoomVO();
//         roomVO.setRoomName(generatedRoomName);
//         return MsgRoomVO;
     }
    

    @GetMapping
    public List<MsgRoomVO> findAllRoom() {
    	System.out.println("!!!!리스트불러오기!!!!" );
        return msgService.findAllRoom();
    }
}