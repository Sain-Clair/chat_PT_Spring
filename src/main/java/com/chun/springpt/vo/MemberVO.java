package com.chun.springpt.vo;

import java.util.Date;

import org.apache.ibatis.type.Alias;
import lombok.Data;

@Alias("mvo")
@Data
public class MemberVO {
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
}
