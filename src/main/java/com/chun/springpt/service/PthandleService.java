package com.chun.springpt.service;

import com.chun.springpt.dao.DietDao;
import com.chun.springpt.dao.PthandleDao;
import com.chun.springpt.vo.DailyTotalVO;
import com.chun.springpt.vo.PthandleVO;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Service
public class PthandleService {

  @Autowired
  private DietDao dietDao;

  @Autowired
  private PthandleDao pthandleDao;
  @Autowired
  private DietService dietService; // DietService 주입

  public List<PthandleVO> ptAllList(PthandleVO pthandleVO) {
    return pthandleDao.ptAllList(pthandleVO);
  }

  public void pthandleToLive(PthandleVO pthandleVO) {
    pthandleDao.pthandleToLive(pthandleVO);
  }


  //  public List<PthandleVO> pthandleAll2(String trainerId){
//    PthandleVO pthandleVO = new PthandleVO();
//    pthandleVO.setTRAINERID(trainerId);
//
//    List<PthandleVO> ptHandles = ptAllList(pthandleVO);
//
//    for (PthandleVO ptHandle : ptHandles) {
//      String userId = ptHandle.getUSERID();
//      System.out.println(userId);
//      DailyTotalVO dailyTotal = dietDao.getTotaldailyinfo(userId);
////      Map<String, Object> recommandTandnagi = dietDao.getRecommandTandangi(userId);
//      ptHandle.setDailyTotal(dailyTotal);
//    }
//    return ptHandles;
//  }
//}
  public List<PthandleVO> pthandleAll2(String trainerId) {
    PthandleVO pthandleVO = new PthandleVO();
    pthandleVO.setTRAINERID(trainerId);

    List<PthandleVO> ptHandles = ptAllList(pthandleVO);

    for (PthandleVO ptHandle : ptHandles) {
      String userId = ptHandle.getUSERID();
      System.out.println(userId);
      DailyTotalVO dailyTotal = dietDao.getTotaldailyinfo(userId);
      Map<String, Object> recommandTandnagi = dietService.GetRecommandTandangi(userId);
      System.out.println(recommandTandnagi+ "adsfsfasf");
      // 권장 탄,단,지 값을 PthandleVO에 설정
      ptHandle.setRecommandTan((BigDecimal) recommandTandnagi.get("p_recommand_tan"));
      ptHandle.setRecommandDan((BigDecimal) recommandTandnagi.get("p_recommand_dan"));
      ptHandle.setRecommandGi((BigDecimal) recommandTandnagi.get("p_recommand_gi"));
      ptHandle.setRecommandCal((BigDecimal) recommandTandnagi.get("p_recommand_cal"));

      ptHandle.setDailyTotal(dailyTotal);
      System.out.println(ptHandle.getDailyTotal());
    }

    System.out.println(ptHandles + "!!!!!!!!!");
    return ptHandles;
  }
}