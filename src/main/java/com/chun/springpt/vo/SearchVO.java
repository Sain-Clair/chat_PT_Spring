package com.chun.springpt.vo;

import org.apache.ibatis.type.Alias;

import lombok.Data;

@Alias("svo")
@Data
public class SearchVO {
    private String nickname;
    private String foodimg;
    private String foodname;
    private double foodcal;
    private double food_tan;
    private double food_dan;
    private double food_gi;
    private String uploaddate;
    private String category;
}
