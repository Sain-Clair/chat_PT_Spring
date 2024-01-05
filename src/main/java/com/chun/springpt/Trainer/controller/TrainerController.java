package com.chun.springpt.Trainer.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.chun.springpt.Trainer.service.TrainerListService;
import com.chun.springpt.Trainer.vo.TrainerVO;

@RestController
public class TrainerController {
	
	@Autowired
	private TrainerListService Tservice;
	
    @GetMapping("/trainerList")
    public List<TrainerVO> getTrainerList(){
        return Tservice.selecTrainerList();
    }
}
