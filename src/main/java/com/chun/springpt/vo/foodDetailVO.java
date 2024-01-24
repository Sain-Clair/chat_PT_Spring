package com.chun.springpt.vo;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.type.Alias;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@Alias("fdvo")
@AllArgsConstructor
public class foodDetailVO 


{
    private int upphotoid;
    private String title;
    private Date eatedate;
    private double foodcal;
    private double carbohydrate;
    private double protein;
    private double fat;
    private String img;    

}

