package com.chun.springpt.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chun.springpt.dao.TrainerDao;
import com.chun.springpt.vo.TrainerVO;

@Service
public class TrainerListService {
    @Autowired
    private TrainerDao dao;

    public List<TrainerVO>selecTrainerList(){
        return dao.selectList();
    }
    public TrainerVO selectTrainerDetail(int id){
        return dao.selectDetail(id);
    }
}
