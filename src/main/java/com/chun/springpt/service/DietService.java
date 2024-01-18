package com.chun.springpt.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chun.springpt.dao.DietDao;
import com.chun.springpt.vo.DailyTotalVO;
import com.chun.springpt.vo.DietVO;
import com.chun.springpt.vo.NutritionVO;
import com.chun.springpt.vo.SearchVO;

import java.util.Map;
import java.util.HashMap;

@Service
public class DietService {
    @Autowired
    private DietDao dietDao;

    // 일주일 마다 먹은 칼로리를 불러옴
    public List<DietVO> selectDietList(String userName, String startPeriod, String endPeriod) {
        return dietDao.Dailyinfo(userName, startPeriod, endPeriod);
    }

    // 권장 칼로리를 구해옴
    public Map<String, Object> getRecommandCal(String userName) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        resultMap.put("userName", userName);
        resultMap.put("result", 0);
        dietDao.getRecommandCal(resultMap);
        return resultMap;
    }

    // 마지막으로 등록된 음식을 불러옴
    public List<DietVO> differ_last(String userName) {
        return dietDao.get_differ_lastday(userName);
    }

    // 일주일치 몸무게 변화량을 구해옴
    public List<DietVO> selectWeightList(String userName, String startPeriod, String endPeriod) {
        return dietDao.getWeekWeight(userName, startPeriod, endPeriod);
    }

    // 목표 몸무게를 불러옴
    public DietVO getTargetWeight(String userName) {
        return dietDao.getTargetWeight(userName);
    }

    // 유저 권장 칼로리, 탄단지 구해옴
    public Map<String, Object> GetRecommandTandangi(String userName) {
        Map<String, Object> params = new HashMap<>();
        params.put("userName", userName);
        dietDao.getRecommandTandangi(params); // 저장 프로시저 호출
        return params; // OUT 매개변수가 포함된 결과 맵 반환

    }

    // 일주일 평균 탄단지 불러옴
    public NutritionVO getWeekAvgTandangi(String userName, String startPeriod, String endPeriod) {
        return dietDao.getWeekAvgTandangi(userName, startPeriod, endPeriod);
    }

    // 가장 많이 함유된 영양소 음식 3가지 불러옴
    public List<NutritionVO> getTanTop3() {
        return dietDao.getTanTop3();
    }

    public List<NutritionVO> getDanTop3() {
        return dietDao.getDanTop3();
    }

    public List<NutritionVO> getGiTop3() {
        return dietDao.getGiTop3();
    }

    public List<NutritionVO> getCalTop3() {
        return dietDao.getCalTop3();
    }

    // 검색
    public List<SearchVO> SearchCategory(String category, String userName) {
        return dietDao.searchCategory(category, userName);
    }

    public List<SearchVO> SearchPurpose(int purpose, String userName) {
        return dietDao.searchPurpose(purpose, userName);
    }

    public List<SearchVO> SearchAge(int age, int agemax, String userName) {
        return dietDao.searchAge(age, agemax, userName);
    }

    // 오늘의 총합 칼로리, 탄수화물, 단백질, 지방을 불러옴
    public DailyTotalVO getTotaldailyinfo(String userName) {
        return dietDao.getTotaldailyinfo(userName);
    }

    public Map<String, Object> getConsecutive_Dates(String userName) {
        Map<String, Object> params = new HashMap<>();
        params.put("userName", userName);
        dietDao.getConsecutive_Dates(params); // 저장 프로시저 호출
        return params; // OUT 매개변수가 포함된 결과 맵 반환

    }

}
