package com.chun.springpt.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.chun.springpt.vo.FoodVO;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface FoodDao {
    public List<FoodVO> selectFoodList();

    public FoodVO selecOnetFood(@Param("foodnum")int foodnum);

}
