package com.chun.springpt.service;

import com.chun.springpt.dao.DietDao;
import com.chun.springpt.dao.PthandleDao;
import com.chun.springpt.vo.DailyTotalVO;
import com.chun.springpt.vo.PthandleVO;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PthandleService {

  @Autowired
  private DietDao dietDao;

  @Autowired
  private PthandleDao pthandleDao;

  public List<PthandleVO> ptAllList(PthandleVO pthandleVO){
    return pthandleDao.ptAllList(pthandleVO);
  }

  public void pthandleToLive(PthandleVO pthandleVO) {
    System.out.println(pthandleVO.getUSERID());
    System.out.println(pthandleVO.getPTSTART());
    pthandleDao.pthandleToLive(pthandleVO);
  }



  public List<PthandleVO> pthandleAll2(String trainerId){
    PthandleVO pthandleVO = new PthandleVO();
    pthandleVO.setTRAINERID(trainerId);

    List<PthandleVO> ptHandles = ptAllList(pthandleVO);

    for (PthandleVO ptHandle : ptHandles) {
      String userId = ptHandle.getUSERID();
      DailyTotalVO dailyTotal = dietDao.getTotaldailyinfo(userId);
      ptHandle.setDailyTotal(dailyTotal);
    }

    return ptHandles;
  }
}
