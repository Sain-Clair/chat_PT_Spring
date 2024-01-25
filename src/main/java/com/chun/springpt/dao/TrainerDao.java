package com.chun.springpt.dao;

import java.util.List;

import com.chun.springpt.vo.MemberVO;
import org.apache.ibatis.annotations.Mapper;

import com.chun.springpt.vo.TrainerVO;

@Mapper
public interface TrainerDao {
    // 트레이너 리스트
    public List<TrainerVO> selectList();

    // 베스트 트레이너 리스트
    public List<TrainerVO> selectBestTrainerList();

    // 선택 Detail
    public TrainerVO selectDetail(String id);

    // 지역 리스트 가져오기
    public List<TrainerVO> selectRegion();
    public int getIsBoolTrainer(String id);
    
    // 나의 지역에 있는 트레이너 리스트 가져오기
    public List<TrainerVO> selectRegionTrainer(String region);
    // 트레이너 상세정보(자기 자신 토큰값 사용)
    public String gettrainerInfo(String TRAINER_ID);

}