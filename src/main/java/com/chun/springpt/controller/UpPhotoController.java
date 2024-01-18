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

import java.io.File;
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

    @GetMapping("/todayPhoto")
    private List<UpPhotoVo> getTodayPhotoList(HttpServletRequest request)
    {
        String authorizationHeader = request.getHeader("Authorization");
        String token = JwtUtil.extractToken(authorizationHeader);
        String user_id = JwtUtil.getID(token);
        return upPhotoService.selectTodayPhotoList(user_id);
    }

    @PostMapping("/deleteFood")
    private ResponseEntity<?> deleteFood(@RequestBody UpPhotoRequest food) {
        int upphotoid = food.getUpphotoid();
        int deletedRows = upPhotoService.deleteFood(upphotoid);

        if (deletedRows > 0) {
            String imagePath = "E:\\chat_PT_Spring\\src\\main\\resources\\static\\images\\upphoto\\" + upphotoid + ".jpg";
            File file = new File(imagePath);
            if (file.exists()) {
                file.delete();
            }
            return ResponseEntity.ok().body("Deletion successful");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Food not found");
        }
    }

    @PostMapping("/updateQuantity")
    private void updateQuantity(@RequestBody Map<String, Object> requestData){
        int upphotoid = (Integer) requestData.get("upphotoid");
        int newQuantity = (Integer) requestData.get("newQuantity");

        UpPhotoVo vo = new UpPhotoVo();
        vo.setUpphotoid(upphotoid);
        vo.setMass(newQuantity);

        upPhotoService.updateQuantity(vo);
    }
}
