package com.chun.springpt.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.chun.springpt.vo.ptCalendarVO;

@Mapper
public interface ptCalendarDao 
{
    public List<ptCalendarVO> getMyPtList(String userid);
    public void delMySchedule(String userid);


    
}
