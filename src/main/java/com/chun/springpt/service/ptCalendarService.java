package com.chun.springpt.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chun.springpt.dao.ptCalendarDao;
import com.chun.springpt.vo.ptCalendarVO;
@Service
public class ptCalendarService 
{
    
    @Autowired
    private ptCalendarDao pdao;


    public List<ptCalendarVO> getPtList(String userid)
    {
        return pdao.getMyPtList(userid);  
    }
    
}
