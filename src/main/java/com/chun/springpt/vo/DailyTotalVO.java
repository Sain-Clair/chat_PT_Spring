package com.chun.springpt.vo;

import org.apache.ibatis.type.Alias;

import lombok.Data;

@Alias("dtvo")
@Data
public class DailyTotalVO {
    private double dailyTotalCal;
    private double dailyTotalTan;
    private double dailyTotalDan;
    private double dailyTotalGi;
}
