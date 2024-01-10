package com.chun.springpt.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.chun.springpt.vo.TrainerVO;

@Mapper
public interface TrainerDao {
    //  트레이너 리스트
    public List<TrainerVO> selectList();
    // 베스트 트레이너 리스트
    public List<TrainerVO> selectBestTrainerList();
    // 선택 Detail
    // TrainerVO selectDetail(@Param("tnum") int tnum);
    TrainerVO selectDetail(String trainer_id);

    

}