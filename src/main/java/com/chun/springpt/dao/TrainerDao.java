package com.chun.springpt.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.chun.springpt.vo.TrainerVO;

@Mapper
public interface TrainerDao {
    // Trainer List
    public List<TrainerVO> selectList();

    // 선택 Detail
    // TrainerVO selectDetail(@Param("tnum") int tnum);
    TrainerVO selectDetail(String trainer_id);

}