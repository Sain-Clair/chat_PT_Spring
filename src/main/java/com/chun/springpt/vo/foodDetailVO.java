package com.chun.springpt.vo;

import java.util.List;

import org.apache.ibatis.type.Alias;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@Alias("fvo")
@AllArgsConstructor
public class foodDetailVO 
{
    private int id;
    private String title;
    private String foodImgs;
    private Double cal;
    private Double carbohydrate;
    private Double protein;
    private Double fat;
}
