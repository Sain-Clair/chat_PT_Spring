package com.chun.springpt.vo;

import java.util.Date;

import org.apache.ibatis.type.Alias;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Alias("msgvo")
@Data
public class MessageVO {
  // 메시지 타입 : 입장, 채팅
  public enum MessageType {
    ENTER, TALK
  }

  @JsonProperty("type")
  private MessageType type; // 메시지 타입
  @JsonProperty("roomId")
  private String roomId; // 방번호
  @JsonProperty("sender")
  private String sender; // 메시지 보낸사람
  @JsonProperty("message")
  private String message; // 메시지
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSX")
  private Date logdate; // 보낸시간

}