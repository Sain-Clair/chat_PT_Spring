package com.chun.springpt.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.chun.springpt.service.TrainerListService;
import com.chun.springpt.vo.TrainerVO;

@RestController
public class TrainerController {
    @Autowired
    private TrainerListService Tservice;
    @GetMapping("/trainerList")
    public List<TrainerVO> getTrainerList() {
        return Tservice.selecTrainerList();
    }
    @GetMapping("/trainers/{trainer_id}")
    public TrainerVO getTrainerDetail(@PathVariable("trainer_id") String trainer_id) {
        return Tservice.selectTrainerDetail(trainer_id);
    }
}
