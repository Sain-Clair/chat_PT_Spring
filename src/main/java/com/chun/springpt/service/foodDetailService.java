package com.chun.springpt.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chun.springpt.dao.foodDetailDao;
import com.chun.springpt.vo.foodDetailVO;

@Service
public class foodDetailService 
{
    @Autowired
    private foodDetailDao dao;
    
    public List<foodDetailVO> getNutritionData(String userid) 
    {
        return dao.selectNutritionDataByUserId(userid);
    }


    public List<foodDetailVO> selectedFoodDetails(Map<String , Object> parameters)
    {

        System.out.println("parameters : " + parameters);
        return dao.selectedFoodDetails(parameters);
    }


}
