package com.chun.springpt.vo;

import java.util.Date;

import org.apache.ibatis.type.Alias;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Alias("tvo")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TrainerVO {
   private MemberVO memberVO; // 조인을 위한 인스턴스 변수
   private int TNUM;
   private String TRAINER_ID;
   private String TRAINERCOMMENT;
   private String TRAINERINTRO;
   private String REGION;
   private String AWARDS1;
   private String AWARDS2;
   private String AWARDS3;
   private String AWARDS4;
   private String AWARDS5;
   private Date STARTTIME;
   private Date ENDTIME;
   private String MAINIMAGE;
   private String SUBIMAGE1;
   private String SUBIMAGE2;
   private int ISVERIFIED;
   private String GYM;
}
