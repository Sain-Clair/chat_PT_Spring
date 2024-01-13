package com.chun.springpt.dao;


import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.chun.springpt.vo.DietVO;

import java.util.Map;

@Mapper
public interface DietDao {
    List<DietVO> Dailyinfo(String userName, String startPeriod, String endPeriod);

    Map<String,Object> getRecommandCal(Map<String, Object> resultMap);

    List<DietVO> get_differ_lastday(String userName);
}