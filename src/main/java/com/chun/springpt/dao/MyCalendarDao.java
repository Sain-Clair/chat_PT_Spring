package com.chun.springpt.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.chun.springpt.dto.NutritionDTO;
import com.chun.springpt.vo.MyCalendarVO;

@Mapper
public interface MyCalendarDao 
{
    public List<MyCalendarVO> selectCalendarList(String userid);
    public void insertCalendar(MyCalendarVO my);
    public void delMyCalendar(int num);
    public String getUserReal(int num);
    List<NutritionDTO> selectNutritionDataByUserId(String userid);
}  

