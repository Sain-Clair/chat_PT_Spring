package com.chun.springpt.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.chun.springpt.vo.MyCalendarVO;

@Mapper
public interface MyCalendarDao 
{
    public List<MyCalendarVO> getMyCalendarList(String userid);
    public void insertCalendar(MyCalendarVO my);
    public void delMyCalendar(MyCalendarVO my);
}  

