package com.chun.springpt.service;

import java.util.List;
import java.util.Map;

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
        return dao.selectCalendarList(userid);
    }

    public void deleteMyCalendar(int num)
    {
        dao.delMyCalendar(num);
    }

    public List<MyCalendarVO> sameDataByNnum(List<MyCalendarVO> vo)
    {
        return dao.sameDataByNnum(vo);
    }
    


}