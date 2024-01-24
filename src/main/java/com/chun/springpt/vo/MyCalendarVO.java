package com.chun.springpt.vo;

import java.util.Date;

import org.apache.ibatis.type.Alias;

import lombok.Data;

@Alias("mycalendar")
@Data
public class MyCalendarVO 
{
    private int id;
    private String title;
    private String eatdate;
}
