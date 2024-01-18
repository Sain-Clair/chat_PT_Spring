package com.chun.springpt.vo;

import org.apache.ibatis.type.Alias;

import lombok.Data;

@Alias("mycalendar")
@Data
public class MyCalendarVO 
{
    private String eventNum;
    private String userid;
    private String title;
    private String img;
    private String startStr;
    private String endStr;
}
