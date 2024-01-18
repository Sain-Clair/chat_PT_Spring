package com.chun.springpt.dao;

import com.chun.springpt.vo.FoodUpVO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface FoodUpDao {
    void foodUpload(FoodUpVO vo);
    int getNextUpPhotoId();
    void insertMemberFood(FoodUpVO vo);

    int getNnumByNormalId(String normalId);
}
