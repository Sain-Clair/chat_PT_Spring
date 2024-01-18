package com.chun.springpt.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.chun.springpt.dto.NutritionDTO;
import com.chun.springpt.service.MyCalendarService;
import com.chun.springpt.utils.JwtUtil;
import com.chun.springpt.vo.MyCalendarVO;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@Slf4j
public class MyCalendarController 
{
    @Autowired
    private HttpServletRequest request;

    @Autowired
    private MyCalendarService mservice;
    
    @GetMapping("/myCalendarList")
    public List<MyCalendarVO> getMyCalendarList() 
    {
        String authorizationHeader = request.getHeader("Authorization");
        String token = JwtUtil.extractToken(authorizationHeader);

        // 사용자 아이디
        String userid = JwtUtil.getID(token);
        
        return mservice.selectCalendarList(userid);
    }

    @GetMapping("/myUserId")
    public String getMyUserId()
    {
        String authorizationHeader = request.getHeader("Authorization");
        String token = JwtUtil.extractToken(authorizationHeader);

        // 사용자 아이디
        String userid = JwtUtil.getID(token);

        return userid;
    }

    // @PostMapping("/insertMyCalendar")
    // public void insertCalendar(MyCalendarVO vo)
    // {
    //     String authorizationHeader = request.getHeader("Authorization");
    //     String token = JwtUtil.extractToken(authorizationHeader);

    //     // 사용자 아이디
    //     String userid = JwtUtil.getID(token);

    //     if(vo.getUserid().equals(userid))
    //     {
    //         mservice.insertMyCalendar(vo);
    //     }

    // }

    @DeleteMapping("/delteCalendarInfo/{num}")
    public void delMyCalendar(@PathVariable("num") Integer num)
    {
        String authorizationHeader = request.getHeader("Authorization");
        String token = JwtUtil.extractToken(authorizationHeader);
        
        // 사용자 아이디
        String userid = JwtUtil.getID(token);
        
        String userRealId = mservice.getUserReal(num);
        
        if(userRealId.equals(userid))
        {
            mservice.deleteMyCalendar(num);
        }

    } 


    @GetMapping("/nutrition")
    public List<NutritionDTO> getNutritionData() 
    {
        String authorizationHeader = request.getHeader("Authorization");
        String token = JwtUtil.extractToken(authorizationHeader);
        
        // 사용자 아이디
        String userid = JwtUtil.getID(token);
        
        return mservice.getNutritionDataByUserId(userid);
    }







    
    




}
