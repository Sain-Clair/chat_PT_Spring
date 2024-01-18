package com.chun.springpt.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chun.springpt.dao.MyCalendarDao;
import com.chun.springpt.vo.MyCalendarVO;

@Service
public class MyCalendarService 
{
    @Autowired
    private MyCalendarDao dao;

    public List<MyCalendarVO> selectCalendarList(String userid)
    {
        return dao.getMyCalendarList(userid);
    }

    public void insertMyCalendar(MyCalendarVO my)
    {
       dao.insertCalendar(my);
    }

    public void deleteMyCalendar(MyCalendarVO my)
    {
        dao.delMyCalendar(my);
    }


}
