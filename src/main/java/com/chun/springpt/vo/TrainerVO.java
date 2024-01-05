package com.chun.springpt.vo;
import org.apache.ibatis.type.Alias;

import lombok.Data;

@Alias("tvo")
@Data
public class TrainerVO {
    private String TRAINERNAME;
    private String TRAINERCOMMENT;
    private String TRAINERID;
    private String TRAINERPW;
    private String TRAINERPURPOSE;
    private String TRAINEREMAIL;
    private String TRAINERGENDER;
}
