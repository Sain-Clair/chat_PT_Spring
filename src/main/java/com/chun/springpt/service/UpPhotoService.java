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

    public int deleteFood(int upphotoid, String user_id) {
        log.info("Attempting to delete food with upphotoid: {} for user: {}", upphotoid, user_id);
        int result = upPhotoDao.deleteFood(upphotoid, user_id);
        log.info("Result of deletion: {}", result > 0 ? "Success" : "Failure");
        return result;
    }
}
