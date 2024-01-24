package com.chun.springpt.service;

import com.chun.springpt.dao.PthandleDao;
import com.chun.springpt.vo.PthandleVO;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PthandleService {

  @Autowired
  private PthandleDao pthandleDao;

  public List<PthandleVO> ptAllList(PthandleVO pthandleVO){
    return pthandleDao.ptAllList(pthandleVO);
  }

  public void pthandleToLive(PthandleVO pthandleVO) {
    pthandleDao.pthandleToLive(pthandleVO);
  }

}
