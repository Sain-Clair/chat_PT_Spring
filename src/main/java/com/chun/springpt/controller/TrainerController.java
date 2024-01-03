package com.chun.springpt.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.chun.springpt.vo.TrainerVO;

@RestController
public class TrainerController {
    @GetMapping("/trainerList")
    public List<TrainerVO> getTrainerList(){
        List<TrainerVO> list =  new ArrayList<>();
        
    }
    
}
