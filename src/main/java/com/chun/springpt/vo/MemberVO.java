package com.chun.springpt.vo;

import java.util.Date;

import org.apache.ibatis.type.Alias;
import lombok.Data;

@Alias("mvo")
@Data
public class MemberVO {
	// MEMBERS 에 대한 테이블
	private String ID;
	private String EMAIL;
	private String PASSWORD;
	private String NAME;
	private String GENDER;
	private String ROLE;
	private Date BIRTH;
	private String MEMBER_EMAIL;
	private String KAKAOCODE;
	private String WITHOAUTH;

	
//	NORMAL_MEM 에 대한 테이블
	private float WEIGHT;
	private float HEIGHT;
	private int PURPOSE; //"운동목적 0 : 다이어트 1: 체중유지 2: 벌크업"
	private float ACTIVITY;
	private String NICKNAME;
	private float TARGET_WEIGHT;
	private String nm_PROFILEIMG;
	private String REGION;

}
