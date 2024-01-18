package com.chun.springpt.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chun.springpt.dao.MyCalendarDao;
import com.chun.springpt.dto.NutritionDTO;
import com.chun.springpt.vo.MyCalendarVO;

import lombok.extern.java.Log;

@Service
public class MyCalendarService 
{
    @Autowired
    private MyCalendarDao dao;

    public List<MyCalendarVO> selectCalendarList(String userid)
    {
        return dao.selectCalendarList(userid);
    }

    public void insertMyCalendar(MyCalendarVO my)
    {
       dao.insertCalendar(my);
    }

    public void deleteMyCalendar(int num)
    {
        dao.delMyCalendar(num);
    }

    public String getUserReal(int number)
	{
		return dao.getUserReal(number);
	}

    public List<NutritionDTO> getNutritionDataByUserId(String userid) 
    {
        return dao.selectNutritionDataByUserId(userid);
    }

}