package com.chun.springpt.dao;

import com.chun.springpt.vo.FoodUpVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Mapper
public interface FoodUpDao {
    int getNextUpPhotoId();
    void insertFoodData(FoodUpVO vo);

    void insertUpPhoto(FoodUpVO vo);

    List<Map<String, Object>> selectRatingDate(int nnum);

    void insertMemberFood(Map<String, Object> paramMap);

    void updateMemberFood(Map<String, Object> paramMap);

    int selectNnum(String normalId);

    void deleteMemberFood(int upphotoid);

    void updateSubtractFood(Date currentDate);
}
