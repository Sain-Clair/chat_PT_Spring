package com.chun.springpt.vo;

import lombok.Data;
import org.apache.ibatis.type.Alias;

import java.util.Date;

@Alias("fuvo")
@Data
public class FoodUpVO {
    private int upphotoid;
    private int nnum;
    private String normalId;
    private String category;
    private int foodnum;
    private int candidate1;
    private int candidate2;
    private int candidate3;
    private Double predictrate;
    private Double candidate1rate;
    private Double candidate2rate;
    private Double candidate3rate;
    private Date uploaddate;
}
