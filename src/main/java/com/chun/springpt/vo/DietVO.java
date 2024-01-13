package com.chun.springpt.vo;


import org.apache.ibatis.type.Alias;

import lombok.Data;

@Alias("dvo")
@Data
public class DietVO {
    private String uploadDate; // 'upload_date' 컬럼과 일치
    private float dailyTotal; // 'daily_total' 컬럼과 일치

    private String category;
    private float total_calories_today;
    private float total_calories_yesterday;
    private String food_names;
    private float calorie_difference;

}
