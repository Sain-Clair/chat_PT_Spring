package com.chun.springpt.controller;

import com.chun.springpt.dto.UpPhotoRequest;
import com.chun.springpt.service.UpPhotoService;
import com.chun.springpt.utils.JwtUtil;
import com.chun.springpt.vo.UpPhotoVo;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
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

    @PostMapping("/deleteFood")
    private ResponseEntity<?> deleteFood(@RequestBody UpPhotoRequest food) {
        log.info("Received request to delete food with upphotoid: {}", food.getUpphotoid());

        String authorizationHeader = request.getHeader("Authorization");
        String token = JwtUtil.extractToken(authorizationHeader);
        String user_id = JwtUtil.getID(token);
        log.info("Extracted user ID from token: {}", user_id);

        int upphotoid = food.getUpphotoid();
        int deletedRows = upPhotoService.deleteFood(upphotoid, user_id);

        log.info("Number of rows deleted: {}", deletedRows);

        if (deletedRows > 0) {
            log.info("Deletion successful for upphotoid: {}", upphotoid);
            return ResponseEntity.ok().body("Deletion successful");
        } else {
            log.warn("No food found to delete for upphotoid: {}", upphotoid);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Food not found");
        }
    }
}
