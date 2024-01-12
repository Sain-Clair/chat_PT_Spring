package com.chun.springpt.vo;

import lombok.Data;
import org.apache.ibatis.type.Alias;

import java.util.Date;

@Alias("uptvo")
@Data
public class UpPhotoVo {
    private String UPPHOTOID;
    private String CATEGORY;
    private Date UPLOADDATE;
    private int NNUM;
    private int FOODNUM;
    private int MASS;
}
