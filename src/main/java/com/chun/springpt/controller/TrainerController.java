package com.chun.springpt.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.chun.springpt.service.TrainerService;
import com.chun.springpt.vo.TrainerVO;

@RestController
// @CrossOrigin(origins = "*")
public class TrainerController {
    @Autowired
    private TrainerService Tservice;
    // 트레이너 리스트
    @GetMapping("/trainerList")
    public List<TrainerVO> getTrainerList() {
        return Tservice.selecTrainerList();
    }
    //  best 트레이너 리스트
    @GetMapping("/BestTrainerList")
    public List<TrainerVO> getTopTrainerList() {
        return Tservice.selectBestTrainerList();
    }
    @GetMapping("/trainerList/{trainer_id}")
    public TrainerVO getTrainerDetail(@PathVariable("trainer_id") String trainer_id) {
        return Tservice.selectTrainerDetail(trainer_id);
    }
    
}
