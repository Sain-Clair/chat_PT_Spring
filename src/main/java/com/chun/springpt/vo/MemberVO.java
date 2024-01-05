package com.chun.springpt.vo;

import java.util.Date;

import org.apache.ibatis.type.Alias;
import lombok.Data;

@Alias("mvo")
@Data
public class MemberVO {
	private String userId;
	private String userEmail;
	private String userPw;
	private String userName;
	private String userGender;

	private int userRole;
	private Date userBirth;
	private float userKg;
	private float userHeight;
	private String foodId;

}
