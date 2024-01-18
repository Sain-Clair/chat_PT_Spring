package com.chun.springpt.dto;

import org.apache.ibatis.type.Alias;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@Alias("nto")
@AllArgsConstructor
public class NutritionDTO 
{
    private String photoPath;
    private double carbohydrate;
    private double protein;
    private double fat;
    
}
