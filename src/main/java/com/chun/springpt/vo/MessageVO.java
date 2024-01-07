package com.chun.springpt.vo;

import java.util.Date;
import lombok.Data;

@Data
public class MessageVO {
    public enum MessageType {
        ENTER, COMM
    }
    private MessageType messageType;
    private int chatroomid;
    private String userid;
    private String log;
    private Date logdate;
    private boolean logisread;
}
