package com.chun.springpt.service;

import com.chun.springpt.dao.FoodUpDao;
import com.chun.springpt.vo.FoodUpVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class FoodUpService {
    @Autowired
    private FoodUpDao foodUpDao;

    public void foodUpload(FoodUpVO vo){
        foodUpDao.foodUpload(vo);
    }
    public int getNextUpPhotoId() {
        return foodUpDao.getNextUpPhotoId();
    }

    public void insertMemberFood(FoodUpVO vo){
        foodUpDao.insertMemberFood(vo);
    }

    public int getNnumByNormalId(String normalId){
        return foodUpDao.getNnumByNormalId(normalId);
    }
}
