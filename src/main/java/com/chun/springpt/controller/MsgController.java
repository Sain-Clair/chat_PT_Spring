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
@RequestMapping("/pt_chatroom")
public class MsgController {

    private final MsgService msgService;
    
    

    @PostMapping
    public MsgRoomVO createRoom() {
        System.out.println("!!!!채팅방생성!!!!!");
        return msgService.createRoom();
    	
//    	 int generatedRoomName = generateRoomName(); // MyBatis를 사용하여 방 번호 생성
//         System.out.println("!!!!채팅방생성!!!!!" + generatedRoomName);
//         MessageDao.insertRoom(generatedRoomName); // 방 생성 쿼리 실행
//         MsgRoomVO roomVO = new MsgRoomVO();
//         roomVO.setRoomName(generatedRoomName);
//         return MsgRoomVO;
     }
    
    private int generateRoomName() {
        // 여기서 적절한 로직을 사용하여 방 번호를 생성하고 반환하는 코드를 작성하세요
        // 예: Random 또는 시퀀스 사용 등
        return 2; // 임시로 정수 123을 반환하는 예시
    }

    @GetMapping
    public List<MsgRoomVO> findAllRoom() {
    	System.out.println("!!!!리스트불러오기!!!!" );
        return msgService.findAllRoom();
    }
}