package com.chun.springpt.dao;

import com.chun.springpt.vo.FoodUpVO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface FoodUpDao {
    int getNextUpPhotoId();
    void insertFoodData(FoodUpVO vo);
}
