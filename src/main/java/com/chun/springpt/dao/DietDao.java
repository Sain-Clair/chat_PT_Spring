package com.chun.springpt.dao;


import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.chun.springpt.vo.DailyTotalVO;
import com.chun.springpt.vo.DietVO;
import com.chun.springpt.vo.NutritionVO;
import com.chun.springpt.vo.SearchVO;

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
    // 프로시저를 이용해 권장 탄,단,지를 구해옴
    void getRecommandTandangi(Map<String, Object> params);
    // 일주일 치 평균 탄,단,지 를 구해옴
    NutritionVO getWeekAvgTandangi(String userName, String startPeriod, String endPeriod);

    // 가장 많이 함유된 영양소를 가져옴
    List<NutritionVO> getTanTop3();
    List<NutritionVO> getDanTop3();
    List<NutritionVO> getGiTop3();
    List<NutritionVO> getCalTop3();

    // 검색 값 별로 각각 구해옴
    List<SearchVO> searchCategory(String category, String userName);
    List<SearchVO> searchPurpose(int purpose, String userName);
    List<SearchVO> searchAge(int age, int agemax, String userName);

    DailyTotalVO getTotaldailyinfo(String userName);
    }