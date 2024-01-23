package com.chun.springpt.vo;

import lombok.Data;
import org.apache.ibatis.type.Alias;

@Alias("irvo")
@Data
public class ImgRequestVO {
    private int upphotoid;
    private String imgeditcomment;
    private int before;
    private int after;
}
