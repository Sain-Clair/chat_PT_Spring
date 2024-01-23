package com.chun.springpt.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.chun.springpt.service.ptCalendarService;
import com.chun.springpt.utils.JwtUtil;
import com.chun.springpt.vo.ptCalendarVO;

import jakarta.servlet.http.HttpServletRequest;

@RestController
public class ptCalendarController 
{
 
    @Autowired
    private HttpServletRequest request;

    @Autowired
    private ptCalendarService pCalendarService;

    @GetMapping("/getMyPtList")
    public List<ptCalendarVO> getMyPtListMembers()
    {
        String authorizationHeader = request.getHeader("Authorization");
        String token = JwtUtil.extractToken(authorizationHeader);

        // 사용자 아이디
        String userid = JwtUtil.getID(token);

        return pCalendarService.getPtList(userid);
    }


    

    
}
