package com.chun.springpt.service;

import com.chun.springpt.dao.UpPhotoDao;
import com.chun.springpt.vo.UpPhotoVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UpPhotoService {
    @Autowired
    private UpPhotoDao upPhotoDao;

    public List<UpPhotoVo> selectTodayPhotoList(String user_id){
        return upPhotoDao.todayPhotoList(user_id);
    }
}
