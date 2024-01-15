package com.chun.springpt.vo;


import org.apache.ibatis.type.Alias;

import lombok.Data;

@Alias("dvo")
@Data
public class DietVO {
    private String uploadDate; 
    private float dailyTotal; 

    private String category;
    private float total_calories_today;
    private float total_calories_yesterday;
    private String food_names;
    private float calorie_difference;

    private String dietLogDate;
    private float dietLogKg;
    private float target_Weight;


}
