package com.chun.springpt.vo;

import org.apache.ibatis.type.Alias;

import lombok.Data;

@Alias("fvo")
@Data
public class FoodVO {
    private int FOODNUM;
    private int FOODCAL;
    private String FOODIMG;
    private int FOOD_TAN;
    private int FOOD_DAN;
    private int FOOD_GI;
    private int FOODWEIGHT;
    private String FOODNAME;
}
