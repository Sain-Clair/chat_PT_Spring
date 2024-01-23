package com.chun.springpt.vo;

import java.util.Date;

import org.apache.ibatis.type.Alias;

import lombok.Data;

@Alias("mycalendar")
@Data
public class MyCalendarVO 
{
    private int eventNum;
    private int upphotoid;
    private String title;
    private String startStr;
    private String endStr;
}
