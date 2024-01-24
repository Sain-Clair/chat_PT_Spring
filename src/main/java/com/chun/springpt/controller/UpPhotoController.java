package com.chun.springpt.controller;

import com.chun.springpt.dto.UpPhotoRequest;
import com.chun.springpt.service.S3uploadService;
import com.chun.springpt.service.UpPhotoService;
import com.chun.springpt.utils.JwtUtil;
import com.chun.springpt.vo.ImgRequestVO;
import com.chun.springpt.vo.UpPhotoVo;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
@Slf4j
public class UpPhotoController {
    private final UpPhotoService upPhotoService;

    @Autowired
    public UpPhotoController(UpPhotoService upPhotoService) {
        this.upPhotoService = upPhotoService;
    }
    @Autowired
    private HttpServletRequest request;
    @Autowired
    private S3uploadService s3uploadService;

    @GetMapping("/todayPhoto")

    private List<UpPhotoVo> getTodayPhotoList(HttpServletRequest request)
    {
        String authorizationHeader = request.getHeader("Authorization");
        String token = JwtUtil.extractToken(authorizationHeader);
        String user_id = JwtUtil.getID(token);
        log.info("date : " + date);
        return upPhotoService.selectTodayPhotoList(user_id, date);
    }

    @PostMapping("/deleteFood")
    private ResponseEntity<?> deleteFoodData(@RequestBody UpPhotoRequest food) {
        int upphotoid = food.getUpphotoid();
        log.info("upphotoid : " + upphotoid);
        boolean isDeleteImg = transactionDeleteUpdate(upphotoid);
        log.info("isDeleteImg : " + isDeleteImg);
        if (isDeleteImg) {
            String imagePath = "user_upload_food/" + upphotoid + ".jpg";
            log.info("imagePath : " + imagePath);
            s3uploadService.deleteFileFromS3(imagePath);
            log.info("성공");
            return ResponseEntity.ok().body("Deletion successful");
        } else {
            log.info("실패");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Food not found");
        }
    }

    private boolean transactionDeleteUpdate(int upphotoid) {
        //select
        List<Date> uploaddate = upPhotoService.selectUpLoadDate(upphotoid);

        if (uploaddate.isEmpty()){
            return false;
        }
        else if(uploaddate.size() == 1) {
            //delete
            upPhotoService.deleteMemberFood(upphotoid);
            //update
            upPhotoService.updatePlusFood(uploaddate.get(0));
            return true;
        }else {
            //delete
            upPhotoService.deleteMemberFood(upphotoid);
            return true;
        }
    }

    @PostMapping("/updateQuantity")
    private Map<String,Object> updateQuantity(@RequestBody Map<String, Object> requestData){
        int upphotoid = (Integer) requestData.get("upphotoid");
        int newQuantity = (Integer) requestData.get("newQuantity");

        UpPhotoVo vo = new UpPhotoVo();
        vo.setUpphotoid(upphotoid);
        vo.setMass(newQuantity);

        upPhotoService.updateQuantity(vo);
        return upPhotoService.selectFoodWeight(upphotoid);
    }
    @PostMapping("/requestNameChange")
    private void insertRequest(@RequestBody ImgRequestVO vo){
        upPhotoService.transactionRequestFood(vo);
    }

    @GetMapping("/getRequestFoodName")
    private String getRequestFoodName(@RequestParam int upphotoid){
        log.info("getRequestFoodName upphotoid : " + upphotoid);
        return upPhotoService.getRequestFoodName(upphotoid);
    }
}
