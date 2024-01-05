package com.chun.springpt.vo;

import java.util.Date;

import lombok.Data;

@Data
public class MessageVO {
    public enum MessageType {
        ENTER, COMM
    }
    private MessageType messageType;
    private String chatroomid;
    private String USERID;
    private String LOG;
    private Date LOGDATE;
    private boolean LOGISREAD;
}