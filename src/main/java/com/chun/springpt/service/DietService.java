package com.chun.springpt.service;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chun.springpt.dao.DietDao;
import com.chun.springpt.vo.DietVO;

import java.util.Map;
import java.util.HashMap;
@Service
public class DietService {
    @Autowired
    private DietDao dietDao;

    public List<DietVO> selectDietList(String userName, String startPeriod, String endPeriod) {
        return dietDao.Dailyinfo(userName, startPeriod, endPeriod);
    }

    public Map<String, Object> getRecommandCal(String userName){
        Map<String, Object> resultMap = new HashMap<String, Object>();
        resultMap.put("userName", userName);
        resultMap.put("result",0);
        dietDao.getRecommandCal(resultMap);
        return resultMap;
    }
}
