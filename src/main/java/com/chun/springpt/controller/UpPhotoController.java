package com.chun.springpt.controller;

import com.chun.springpt.service.UpPhotoService;
import com.chun.springpt.utils.JwtUtil;
import com.chun.springpt.vo.UpPhotoVo;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UpPhotoController {
    @Autowired
    private UpPhotoService upPhotoService;
    @Autowired
    private HttpServletRequest request;

    @GetMapping("/todayPhoto")
    private List<UpPhotoVo> getTodayPhotoList(){
        String authorizationHeader = request.getHeader("Authorization");
        String token = JwtUtil.extractToken(authorizationHeader);
        String user_id = JwtUtil.getID(token);
        return upPhotoService.selectTodayPhotoList(user_id);
    }
}
