package com.chun.springpt.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.chun.springpt.service.TrainerService;
import com.chun.springpt.vo.TrainerVO;

import lombok.extern.slf4j.Slf4j;



@RestController
@Slf4j
public class TrainerController {
    @Autowired
    private TrainerService Tservice;
    // 전체 트레이너 리스트
    @GetMapping("/trainerList")
    public List<TrainerVO> getTrainerList() {
        return Tservice.selecTrainerList();
    }
    //  best 트레이너 리스트
    @GetMapping("/BestTrainerListMonth")
    public List<TrainerVO> getBestTrainerListMonth() {
        return Tservice.selectBestTrainerList();
    }
    // 지역 리스트 가져오기
    @GetMapping("/regions")
    public List<TrainerVO> getRegion() {
        return Tservice.
        selectRegion();
    }
    // 지역 선택 후, 지역에 맞는 트레이너
    @GetMapping("/regionTrainer")
    public List<TrainerVO> getRegionTrainer(@RequestParam("region") String region) {
        log.info("로깅 - 지역: {}", region);
        return Tservice.selectRegionTrainer(region);
    }
    
    // 트레이너 상세정보
    @GetMapping("/trainerList/{trainer_id}")
    public TrainerVO getTrainerDetail(@PathVariable("trainer_id") String trainer_id) {
        System.out.println("트레이너 디테일" +trainer_id);
        return Tservice.selectTrainerDetail(trainer_id);
    }
    
}
