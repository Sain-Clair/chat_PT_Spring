package com.chun.springpt.vo;

import org.apache.ibatis.type.Alias;

import lombok.Data;

@Data
@Alias("ptCalendar")
public class ptCalendarVO 
{
    private int scnum;
    private String title;
    private String ptstart;
    private String ptend;
    
}
