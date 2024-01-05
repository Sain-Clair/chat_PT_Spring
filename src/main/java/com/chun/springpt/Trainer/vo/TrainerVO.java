package com.chun.springpt.Trainer.vo;
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
    private String TRAINERNAME;
    private String TRAINERCOMMENT;
    private String TRAINERID;
    private String TRAINERPW;
    private String TRAINERPURPOSE;
    private String TRAINEREMAIL;
    private String TRAINERGENDER;
}
