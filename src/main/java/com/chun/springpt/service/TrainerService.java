package com.chun.springpt.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chun.springpt.dao.TrainerDao;
import com.chun.springpt.vo.TrainerVO;

@Service
public class TrainerService {
    @Autowired
    private TrainerDao dao;
    
    // 트레이너 리스트
    public List<TrainerVO> selecTrainerList() {
        return dao.selectList();
    }
    public List<TrainerVO> selectTopTrainerList(){
        return dao.selectTopTrainerList();
    }
    // 트레이너 상세 정보
    public TrainerVO selectTrainerDetail(String trainer_id) {
        return dao.selectDetail(trainer_id);
    }
}
