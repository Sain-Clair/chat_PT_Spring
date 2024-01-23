package com.chun.springpt.vo;

import java.util.Date;

import org.apache.ibatis.type.Alias;

import lombok.Data;

@Alias("nmmvo")
@Data
public class mem_normal_memVO {
    private String ID;
    private String EMAIL;
    private String PASSWORD;
    private String NAME;
    private String GENDER;
    private String ROLE;
    private Date BIRTH;
    private String MEMBER_EMAIL;

    private int NNUM;
    private String NORMAL_ID;
    private float WEIGHT;
    private float HEIGHT;
    private int PURPOSE;
    private int ACTIVITY;
    private String NICKNAME;
    private String TARGET_WEIGHT;
    private String NM_PROFILEIMG;

}
