package com.chun.springpt.vo;

import lombok.Data;
import org.apache.ibatis.type.Alias;

import java.util.Date;

@Alias("uptvo")
@Data
public class UpPhotoVo {
    private int UPPHOTOID;
    private String CATEGORY;
    private Date UPLOADDATE;
    private int NNUM;
    private int FOODNUM;
    private int CANDIDATE1;
    private int CANDIDATE2;
    private int CANDIDATE3;
    private int PREDICTRATE;
    private int CANDIDATE1RATE;
    private int CANDIDATE2RATE;
    private int CANDIDATE3RATE;


}
