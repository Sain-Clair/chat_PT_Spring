package com.chun.springpt.dao;


import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.chun.springpt.vo.DietVO;

import java.util.Map;

@Mapper
public interface DietDao {
    // 주마다 먹은 칼로리를 구해옴
    List<DietVO> Dailyinfo(String userName, String startPeriod, String endPeriod);
    // 프로시저를 이용해 권장 칼로리를 구해옴
    Map<String,Object> getRecommandCal(Map<String, Object> resultMap);
    // 전날 대비 얼마나 먹었는지를 구해옴
    List<DietVO> get_differ_lastday(String userName);
    // 일주일치 몸무게 변화량을 구해옴
    List<DietVO> getWeekWeight(String userName, String startPeriod, String endPeriod);
    // 목표 몸무게를 구해옴
    DietVO getTargetWeight(String userName);
}