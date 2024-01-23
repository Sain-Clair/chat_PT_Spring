package com.chun.springpt.dao;


import com.chun.springpt.vo.ImgRequestVO;
import com.chun.springpt.vo.UpPhotoVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Mapper
public interface UpPhotoDao {
    List<UpPhotoVo> todayPhotoList(String user_id, String date);
    int deleteFoodData(int upphotoid);
    void updateQuantity(UpPhotoVo vo);

    Map<String,Object> selectFoodWeight(int upphotoid);

    void insertRequestFood(ImgRequestVO vo);

    void deleteMemberFood(int upphotoid);

    List<Date> selectUpLoadDate(int upphotoid);

    void updatePlusFood(Date uploaddate);

    void updateUpphotoFoodnum(ImgRequestVO vo);

    String getRequestFoodName(int upphotoid);
}
