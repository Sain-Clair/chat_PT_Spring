package com.chun.springpt.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.chun.springpt.vo.FoodVO;

@Mapper
public interface FoodDao {
    public List<FoodVO> selectFoodList();

}
