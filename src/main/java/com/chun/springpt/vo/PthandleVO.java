package com.chun.springpt.vo;


import lombok.Data;
import org.apache.ibatis.type.Alias;

import java.util.Date;

@Alias("ptHandle")
@Data
public class PthandleVO {

  // MATCHING, MEMBERS, NORMAL_MEM 3개의 테이블 JOIN하여 /
  //  M.NAME, M.BIRTH, M.GENDER, N.PURPOSE,
  //  N.WEIGHT, N.HEIGHT, N.TARGET_WEIGHT 을 갖고 옵니다.
  // 파라미터는 TRAINERID
  private String TRAINERID; //파라미터

  private String NAME; //이름
  private Date BIRTH; // 생년월일
  private String GENDER; // 성별
  private int PURPOSE; //운동목적
  private float WEIGHT; //현재 몸무게
  private float HEIGHT; //현재 키
  private float TARGET_WEIGHT; //목표 몸무게
  private Date PTSTART;
  private Date PTEND;
  private String STATUS;
  private String USERID;
  private DailyTotalVO dailyTotal; // 일일 영양 정보를 저장할 필드

}
