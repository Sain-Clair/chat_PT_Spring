package com.chun.springpt.vo;
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
   private int TNUM;
   private String TRAINER_ID;
   private String TRAINERCOMMENT;
   private String TRAINERINTRO;
   private String ADDRESS;
   private String CONTACTTIME;
   private String AWARDS1;
   private String AWARDS2;
   private String AWARDS3;
   private String AWARDS4;
   private String AWARDS5;
   private String STATUS;

}
