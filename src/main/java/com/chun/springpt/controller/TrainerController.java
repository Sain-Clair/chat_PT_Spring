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
    @GetMapping("/trainers/{id}")
    public TrainerVO getTrainerDetail(@PathVariable int id) {
        return Tservice.selectTrainerDetail(id);
    }
}