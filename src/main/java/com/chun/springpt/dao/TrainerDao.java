package com.chun.springpt.dao;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;

import com.chun.springpt.vo.TrainerVO;


@Mapper
public interface TrainerDao {
    List<TrainerVO> selectList();
}