package com.chun.springpt.service;

import com.chun.springpt.dao.UpPhotoDao;
import com.chun.springpt.vo.UpPhotoVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class UpPhotoService {
    @Autowired
    private UpPhotoDao upPhotoDao;

    public List<UpPhotoVo> selectTodayPhotoList(String user_id){
        return upPhotoDao.todayPhotoList(user_id);
    }

    public int deleteFood(int upphotoid) {
        return upPhotoDao.deleteFood(upphotoid);
    }
    public void updateQuantity(UpPhotoVo vo){
        upPhotoDao.updateQuantity(vo);
    }
}
