package com.chun.springpt.Trainer.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.chun.springpt.Trainer.vo.TrainerVO;

@Mapper
public interface TrainerDao {
    List<TrainerVO> selectList();
}