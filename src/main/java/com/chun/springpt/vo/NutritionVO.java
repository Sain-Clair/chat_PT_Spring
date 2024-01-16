package com.chun.springpt.vo;

import org.apache.ibatis.type.Alias;

import lombok.Data;

@Alias("nvo")
@Data
public class NutritionVO {
    private double weeklyAvg_tan;
    private double weeklyAvg_dan;
    private double weeklyAvg_gi;

    private double ratio;
    private String Foodname;
    private String foodimg;
}
