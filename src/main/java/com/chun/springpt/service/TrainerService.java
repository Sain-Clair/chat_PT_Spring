package com.chun.springpt.service;

import java.util.List;

import com.chun.springpt.vo.MemberVO;
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

    // 베스트 트레이너 리스트
    public List<TrainerVO> selectBestTrainerList() {
        return dao.selectBestTrainerList();
    }

    // 트레이너 상세 정보
    public TrainerVO selectTrainerDetail(String trainer_id) {
        return dao.selectDetail(trainer_id);
    }

    // 지역 리스트 가져오기
    public List<TrainerVO> selectRegion() {
        return dao.selectRegion();
    }

    // 지역 선택 후 트레이너 리스트 가져오기
    public List<TrainerVO> selectRegionTrainer(String region) {
        return dao.selectRegionTrainer(region);
    }

    public int getIsBoolTrainer(String trainer_id)
    {
        return 0;
    }

    // 트레이너 상세정보(자기 자신 토큰값 사용)
    public String gettrainerInfo(String TRAINER_ID) {
        return dao.gettrainerInfo(TRAINER_ID);
    }
}
