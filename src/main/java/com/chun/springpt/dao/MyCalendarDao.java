package com.chun.springpt.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.chun.springpt.vo.MyCalendarVO;

@Mapper
public interface MyCalendarDao 
{
    public List<MyCalendarVO> selectCalendarList(Map<String , Object> params);
    public void delMyCalendar(int num);
    public List<MyCalendarVO> sameDataByNnum(List<MyCalendarVO> vo);
}  

