package com.chun.springpt.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.chun.springpt.service.MyCalendarService;
import com.chun.springpt.vo.MyCalendarVO;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
public class MyCalendarController 
{

    @Autowired
    private MyCalendarService mservice;

    
    @GetMapping("/myCalendarList")
    public List<MyCalendarVO> getMyCalendarList(@RequestParam String userid) 
    {
        return mservice.selectCalendarList(userid);
    }
    

    @PostMapping("/inserCalendar")
    public void insertCalendar(MyCalendarVO vo)
    {
        mservice.insertMyCalendar(vo);
    }


    @DeleteMapping("delteCalendarInfo")
    public void deleteMyCalendar(MyCalendarVO vo)
    {
        mservice.deleteMyCalendar(vo);
    }




    
    




}
