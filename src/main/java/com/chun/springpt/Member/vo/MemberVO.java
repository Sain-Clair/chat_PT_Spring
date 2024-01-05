package com.chun.springpt.Member.vo;



import java.sql.Date;
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
	private String userpupose;
	private float userKg;
	private float userHeight;
	private String foodId;
}
