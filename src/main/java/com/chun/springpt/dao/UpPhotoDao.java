package com.chun.springpt.dao;


import com.chun.springpt.vo.UpPhotoVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UpPhotoDao {
    List<UpPhotoVo> todayPhotoList(String user_id);
    int deleteFood(int upphotoid);
    void updateQuantity(UpPhotoVo vo);
}
