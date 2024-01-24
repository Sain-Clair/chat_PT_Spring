package com.chun.springpt.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.chun.springpt.vo.foodDetailVO;

@Mapper
public interface foodDetailDao 
{
    List<foodDetailVO> selectNutritionDataByUserId(String userid);
    List<foodDetailVO> selectedFoodDetails(Map<String,Object> parameters);
}
