package com.chun.springpt.service;

import com.chun.springpt.dao.UpPhotoDao;
import com.chun.springpt.vo.ImgRequestVO;
import com.chun.springpt.vo.UpPhotoVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class UpPhotoService {
    @Autowired
    private UpPhotoDao upPhotoDao;

    public List<UpPhotoVo> selectTodayPhotoList(String user_id){
        return upPhotoDao.todayPhotoList(user_id);
    }

    public int deleteFoodData(int upphotoid) {
        return upPhotoDao.deleteFoodData(upphotoid);
    }
    public void updateQuantity(UpPhotoVo vo){
        upPhotoDao.updateQuantity(vo);
    }
    public Map<String,Object> selectFoodWeight(int upphotoid){
        return upPhotoDao.selectFoodWeight(upphotoid);
    }
    public void insertRequestFood(ImgRequestVO vo){
        upPhotoDao.insertRequestFood(vo);
    }

}
