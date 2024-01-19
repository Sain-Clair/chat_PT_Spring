package com.chun.springpt.service;

import com.chun.springpt.dao.FoodUpDao;
import com.chun.springpt.vo.FoodUpVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class FoodUpService {
    @Autowired
    private FoodUpDao foodUpDao;

    public int getNextUpPhotoId() {
        return foodUpDao.getNextUpPhotoId();
    }
    public void insertFoodData(FoodUpVO vo){
        foodUpDao.insertFoodData(vo);
    }
    public void insertUpPhoto(FoodUpVO vo){
        foodUpDao.insertUpPhoto(vo);
    }
    public List<Map<String,Object>> selectRatingDate(int nnum){
        return foodUpDao.selectRatingDate(nnum);
    }
    public void insertMemberFood(Map<String,Object> paramMap){
        foodUpDao.insertMemberFood(paramMap);
    }
    public void updateMemberFood(Map<String,Object> paramMap){
        foodUpDao.updateMemberFood(paramMap);
    }

    public int selectNnum(String normalId) {
        return foodUpDao.selectNnum(normalId);
    }

    public void deleteMemberFood(int upphotoid) {
        foodUpDao.deleteMemberFood(upphotoid);
    }

    public void updateSubtractFood(Date currentDate) {
        foodUpDao.updateSubtractFood(currentDate);
    }
}
