package com.chun.springpt.controller;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import com.chun.springpt.service.S3uploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.chun.springpt.service.MemberService;
import com.chun.springpt.utils.JwtUtil;
import com.chun.springpt.vo.MemberVO;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class MemberController {
    @Autowired
    private HttpServletRequest request;
    @Autowired
    private MemberService Mservice;
    @Autowired
    private S3uploadService s3uploadService;

    @GetMapping("/a_userList")
    public List<MemberVO> listMember() {
        return Mservice.selectMemberList();
    }

    @GetMapping("getMyRegion")
    public String getMyRegion() {
        String authorizationHeader = request.getHeader("Authorization");
        String token = JwtUtil.extractToken(authorizationHeader);
        String userName = JwtUtil.getID(token);

        return Mservice.getRegion(userName);
    }

    // 몸무게 수정
    @PostMapping("/changeWeight")
    @ResponseBody
    public String changeWeight(@RequestBody Map<String, String> map) {
        String authorizationHeader = request.getHeader("Authorization");
        String token = JwtUtil.extractToken(authorizationHeader);
        String userName = JwtUtil.getID(token);

        Mservice.changeWeight(userName, Integer.parseInt(map.get("weight")));

        return "success";
    }

    // 특정 한명에 대한 정보 가져오기
    @GetMapping("/getuserInfo")
    @ResponseBody
    public List<MemberVO> getuserInfo(MemberVO memberVO) {
        String authorizationHeader = request.getHeader("Authorization");
        String token = JwtUtil.extractToken(authorizationHeader);
        String userName = JwtUtil.getID(token);

        memberVO.setID(userName);

        return Mservice.getuserInfo(memberVO);
    }

    // 회원 정보 수정
    @PostMapping("/updateUserInfo")
    public ResponseEntity<?> updateUserInfo(@RequestBody MemberVO memberVO) {
        String authorizationHeader = request.getHeader("Authorization");
        String token = JwtUtil.extractToken(authorizationHeader);
        String userName = JwtUtil.getID(token);

        memberVO.setID(userName); // 토큰에서 추출한 사용자 ID 설정
//        System.out.println("유저 업데이트 요청! " + memberVO);
        try {
            Mservice.updateUserInfo(memberVO); // 업데이트 메서드 호출

//            System.out.println("유저 업데이트 완료: " + memberVO.getID());
            return ResponseEntity.ok().body("유저 업데이트 요청 완료 📀");
        } catch (Exception e) {
            System.err.println("업데이트 중 예외 발생: " + e.getMessage());
            e.printStackTrace(); // 예외 스택 추적 출력
            return ResponseEntity.badRequest().body("업데이트 실패");
        }
    }
    @PostMapping("/updateprofileimg")
    public List<String> s3upload(@RequestPart(required = false) List<MultipartFile> uploadImgs) throws IOException {
        if (uploadImgs == null) {
            // 이 부분에 원하는 로직을 추가하세요.
//            System.out.println("uploadImgs : null!!!!!!!!!!!!");
            return Collections.emptyList(); // 빈 리스트를 반환하거나 다른 적절한 처리를 수행하세요.
        }

        // 파일 업로드 처리
        return s3uploadService.saveFile("normal_user/profile_img/", uploadImgs);
    }

}