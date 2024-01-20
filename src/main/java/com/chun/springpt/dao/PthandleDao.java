package com.chun.springpt.dao;

import com.chun.springpt.vo.PthandleVO;
import com.chun.springpt.vo.TrainerVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface PthandleDao {
  public List<PthandleVO> ptAllList(PthandleVO pthandleVO);
  public List<PthandleVO> ptAllList2(PthandleVO pthandleVO);

  public void pthandleToLive(PthandleVO pthandleVO);


}
