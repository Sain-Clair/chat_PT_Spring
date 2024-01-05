package com.chun.springpt.Trainer.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.chun.springpt.Trainer.vo.TrainerVO;

@Mapper
public interface TrainerDao {
    // Trainer List
    List<TrainerVO> selectList();

    // 선택 Detail
    TrainerVO selectDetail(@Param("id") int id);

}