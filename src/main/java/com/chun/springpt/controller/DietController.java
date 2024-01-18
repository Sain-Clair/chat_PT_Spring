package com.chun.springpt.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;

import com.chun.springpt.service.DietService;
import com.chun.springpt.utils.JwtUtil;
import com.chun.springpt.vo.DailyTotalVO;
import com.chun.springpt.vo.DietVO;
import com.chun.springpt.vo.NutritionVO;
import com.chun.springpt.vo.SearchVO;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@Slf4j
public class DietController {
    @Autowired
    private HttpServletRequest request;

    @Autowired
    private DietService Dservice;

    @GetMapping("/food_recommand")
    public Map<String, Object> foodRecommand() {
        // 헤더에서 토큰 추출
        String authorizationHeader = request.getHeader("Authorization");
        String token = JwtUtil.extractToken(authorizationHeader);

        // 사용자 아이디
        String userName = JwtUtil.getID(token);
        log.info("로깅 - 요청아이디: {}", userName);

        // 외부 URL로부터 데이터 가져오기
        String url = "http://localhost:9000/dlmodel/getCal?id=" + userName;
        RestTemplate restTemplate = new RestTemplate();
        Map<String, Object> response = restTemplate.getForObject(url, Map.class);

        // 외부 URL로부터 받은 데이터 반환
        return response;
    }

    @GetMapping("/diet_cal_analysis")
    public Map<String, Object> getDietList(@RequestParam("startPeriod") String startPeriod,
            @RequestParam("endPeriod") String endPeriod, HttpServletRequest request) {
        String authorizationHeader = request.getHeader("Authorization");
        String token = JwtUtil.extractToken(authorizationHeader);

        // 사용자 아이디
        String userName = JwtUtil.getID(token);
        log.info("로깅 - 요청아이디: {}", userName);

        List<DietVO> dietList = Dservice.selectDietList(userName, startPeriod, endPeriod);
        Map<String, Object> recommandCal = Dservice.getRecommandCal(userName);
        BigDecimal recommandCalBigDecimal = (BigDecimal) recommandCal.get("result");
        Double recommandCalDouble = recommandCalBigDecimal.doubleValue();
        log.info("로깅 - 권장 칼로리: {}", recommandCalDouble);

        List<DietVO> differ_last = Dservice.differ_last(userName);

        Map<String, Object> response = new HashMap<>();
        response.put("dietList", dietList);
        response.put("last_differ", differ_last);
        response.put("recommandCal", recommandCal.get("result"));

        return response; // JSON 형식으로 데이터를 반환합니다.
    }

    @GetMapping("diet_weight_analysis")
    public Map<String, Object> getWeightList(@RequestParam("startPeriod") String startPeriod,
            @RequestParam("endPeriod") String endPeriod) {
        String authorizationHeader = request.getHeader("Authorization");
        String token = JwtUtil.extractToken(authorizationHeader);

        // 사용자 아이디
        String userName = JwtUtil.getID(token);
        log.info("로깅 - 요청아이디: {}", userName);
        List<DietVO> weightList = Dservice.selectWeightList(userName, startPeriod, endPeriod);

        DietVO targetWeight = Dservice.getTargetWeight(userName);

        Map<String, Object> response = new HashMap<>();
        response.put("weightList", weightList);
        response.put("targetWeight", targetWeight);

        return response; // JSON 형식으로 데이터를 반환합니다.

    }

    @GetMapping("/getRecommandTandangi")
    public Map<String, Object> getRecommandTandangi(@RequestParam("startPeriod") String startPeriod,
            @RequestParam("endPeriod") String endPeriod) {

        String authorizationHeader = request.getHeader("Authorization");
        String token = JwtUtil.extractToken(authorizationHeader);

        // 사용자 아이디
        String userName = JwtUtil.getID(token);
        Map<String, Object> recommandTandnagi = Dservice.GetRecommandTandangi(userName);
        NutritionVO avgTanDanGi = Dservice.getWeekAvgTandangi(userName, startPeriod, endPeriod);

        Map<String, Object> response = new HashMap<>();
        response.put("recommandTandnagi", recommandTandnagi);
        response.put("avgTanDanGi", avgTanDanGi);
        return response;
    }

    @GetMapping("/food_top3")
    public List<NutritionVO> getfood_top3(@RequestParam("chooseone") String param) {
        log.info(param);
        if (param.equals("food_tan")) {
            return Dservice.getTanTop3();
        } else if (param.equals("food_dan")) {
            return Dservice.getDanTop3();
        } else if (param.equals("food_gi")) {
            return Dservice.getGiTop3();
        } else {
            log.info("else branch executed");
            return Dservice.getCalTop3();
        }
    }


    @GetMapping("/searchOption")
    public List<SearchVO> getSearchData(@RequestParam("selectedCategory")String selectedCategory,
    @RequestParam("selectedSubCategory")String selectedSubCategory) {
        String authorizationHeader = request.getHeader("Authorization");
        String token = JwtUtil.extractToken(authorizationHeader);

        // 사용자 아이디
        String userName = JwtUtil.getID(token);

        if(selectedCategory.equals("연령대 별 보기")){
            int age = 0;
            if(selectedSubCategory.equals("10대")){
                age = 10;
            }else if(selectedSubCategory.equals("20대")){
                age = 20;
            }else if(selectedSubCategory.equals("30대")){
                age = 30;
            }else if(selectedSubCategory.equals("40대")){
                age = 40;
            }else if(selectedSubCategory.equals("50대")){
                age = 50;
            }else if(selectedSubCategory.equals("60대")){
                age = 60;
            }else if(selectedSubCategory.equals("70대")){
                age = 70;

            }
            int agemax = age +9;

            return Dservice.SearchAge(age, agemax, userName);

        }else if(selectedCategory.equals("먹은 시간별 보기")){
            return Dservice.SearchCategory(selectedSubCategory, userName);
        }else if(selectedCategory.equals("식단 목적별 보기")){
            int purpose = 100;
            if(selectedSubCategory.equals("다이어트")){
                purpose = 0;
            }else if(selectedSubCategory.equals("체중유지")){
                purpose = 1;
            }else if(selectedSubCategory.equals("벌크업")){
                purpose = 2;
            }
            return Dservice.SearchPurpose(purpose, userName);
        }
        return null;

    }
    
    @GetMapping("/getRecommandDailyTandangi")
    public Map<String, Object> getRecommandDailyTandangi() {

        String authorizationHeader = request.getHeader("Authorization");
        String token = JwtUtil.extractToken(authorizationHeader);

        // 사용자 아이디
        String userName = JwtUtil.getID(token);
        Map<String, Object> recommandTandnagi = Dservice.GetRecommandTandangi(userName);
        DailyTotalVO dailytotal = Dservice.getTotaldailyinfo(userName);

        Map<String, Object> response = new HashMap<>();
        response.put("recommandTandnagi", recommandTandnagi);
        response.put("totaldaily", dailytotal);
        return response;
    }

}