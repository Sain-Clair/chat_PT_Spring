package com.chun.springpt.Trainer.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chun.springpt.Trainer.dao.TrainerDao;
import com.chun.springpt.Trainer.vo.TrainerVO;

@Service
public class TrainerListService {
    @Autowired
    private TrainerDao dao;

    public List<TrainerVO>selecTrainerList(){
        return dao.selectList();
    }
    
}
