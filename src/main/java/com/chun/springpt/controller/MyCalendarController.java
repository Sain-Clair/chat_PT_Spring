package com.chun.springpt.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import com.chun.springpt.service.MyCalendarService;
import com.chun.springpt.service.UserService;
import com.chun.springpt.service.foodDetailService;
import com.chun.springpt.utils.JwtUtil;
import com.chun.springpt.vo.MyCalendarVO;
import com.chun.springpt.vo.foodDetailVO;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@RestController
@Slf4j
public class MyCalendarController 
{
    @Autowired
    private HttpServletRequest request;

    @Autowired
    private MyCalendarService mservice;
    
    @Autowired
    private foodDetailService nuservice;

    @GetMapping("/myCalendarList")
    public List<MyCalendarVO> getMyCalendarList(@Param("category") String category) 
    {
        String authorizationHeader = request.getHeader("Authorization");
        String token = JwtUtil.extractToken(authorizationHeader);

        // 사용자 아이디
        String userid = JwtUtil.getID(token);

        Map<String , Object> params = new HashMap<String , Object>();
        params.put("userid", userid);
        params.put("category", category);
        

        return mservice.selectCalendarList(params);
    }

    @DeleteMapping("/delteCalendarInfo/{num}")
    public void delMyCalendar(@PathVariable("num") Integer num)
    {
        String authorizationHeader = request.getHeader("Authorization");
        String token = JwtUtil.extractToken(authorizationHeader);
        
        // 사용자 아이디
        String userid = JwtUtil.getID(token);
        
        if(userid != null)
        {
            mservice.deleteMyCalendar(num);
        }
    }

    @GetMapping("/selectedFoodDetails")
    public List<foodDetailVO> getNutritionData(    @Param("category") String category, 
                                                   @Param("date") String date) 
    {
        String authorizationHeader = request.getHeader("Authorization");
        String token = JwtUtil.extractToken(authorizationHeader);
        
        // 사용자 아이디
        String userid = JwtUtil.getID(token);
        Map<String, Object> tmap = new HashMap<String, Object>();
        tmap.put("userid", userid);
        tmap.put("date", date);
        tmap.put("category", category);

        return nuservice.selectedFoodDetails(tmap);
    }
}
