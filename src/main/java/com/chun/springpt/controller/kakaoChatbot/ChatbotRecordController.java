package com.chun.springpt.controller.kakaoChatbot;

import com.chun.springpt.dao.FoodDao;
import com.chun.springpt.service.FoodUpService;
import com.chun.springpt.service.S3uploadService;
import com.chun.springpt.service.kakaoChatbot.KakaoChatbotService;
import com.chun.springpt.vo.FoodUpVO;
import com.chun.springpt.vo.FoodVO;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import static java.util.Map.entry;

@RestController
@Slf4j
@EnableAsync
@SuppressWarnings("unchecked")
@RequestMapping(value = "/chatbot")
public class ChatbotRecordController {

    @Autowired
    private FoodDao foodDao;

    @Autowired
    private KakaoChatbotService kakaoChatbotService;

    @Autowired
    private S3uploadService s3uploadService;

    @Autowired
    private FoodUpService foodUpService;

    @RequestMapping("/record_meal")
    @ResponseBody
    public Map<String, Object> recordMeal(@RequestBody String body) throws ParseException {

        JSONObject loadJson = new JSONObject(body);
        
        // 몇 인분 먹었는지 받기
        String portion = loadJson.getJSONObject("action").getJSONObject("detailParams").getJSONObject("amount").optString("origin", "none");

        // 몇 인분 먹었는지 받은 값이 유효하지 않으면 return
        if (portion.equals("none") || (!portion.isEmpty() && !Character.isDigit(portion.charAt(0)))) {
            return Map.of(
                "version", "2.0",
                "template", Map.of(
                    "outputs", new Object[]{
                        Map.of(
                            "simpleText", Map.of(
                                "text", "'1인분' 또는 '1' 형식으로 다시 입력해주세요."
                            )
                        )
                    }
                )
            );
        }

        // '인분' 제거, 숫자로 변환 
        double integerPortion = Double.parseDouble(portion.replace("인분", "").trim());

        String plusfriendUserKey = kakaoChatbotService.getPlusfriendUserKey(body);
        String userName = kakaoChatbotService.getUserNameByPlusfriendUserKey(plusfriendUserKey);

        // 식사시간이 none이면 유효하지 않은 요청입니다.
        String meal_time = loadJson.getJSONObject("action").getJSONObject("clientExtra").optString("식사시간", "none");
        if (meal_time.equals("none")) {
            return Map.of(
                "version", "2.0",
                "template", Map.of(
                    "outputs", new Object[]{
                        Map.of(
                            "simpleText", Map.of(
                                "text", "유효하지 않은 요청입니다."
                            )
                        )
                    }
                )
            );
        }

        // userName이 null이면 연동코드와 연결된 계정이 없습니다.
        if (userName == null) {
            return Map.of(
                "version", "2.0",
                "template", Map.of(
                    "outputs", new Object[]{
                        Map.of(
                            "simpleText", Map.of(
                                "text", "유효하지 않은 요청입니다."
                            )
                        )
                    }
                )
            );
        }

        // 받은 사진
        String pictureUrl = loadJson.getJSONObject("action").getJSONObject("clientExtra").optString("받은사진", "none");
        URL downloadurl = null;
        String imageName = "";

        // 식단 등록 시퀀스. 시퀀스이자 사람이름이다.
        int upphotoid = foodUpService.getNextUpPhotoId();

        // 사진 S3에 저장하기
        try {
            downloadurl = new URL(pictureUrl);
            InputStream imageInputStream = downloadurl.openStream();
            imageName = upphotoid + ".jpg";
            s3uploadService.saveWithImageInputStream("user_upload_food/", imageName ,imageInputStream);
            
        // 사진이 저장되지 않으면 유효시간이 지났습니다.
        } catch (IOException e) {
            return Map.of(
                "version", "2.0",
                "template", Map.of(
                    "outputs", new Object[]{
                        Map.of(
                            "simpleText", Map.of(
                                "text", "데이터를 저장할 수 있는 유효시간이 지났습니다."
                            )
                        )
                    }
                )
            );
        }

        // 음식 정보 추출
        String mealTime = loadJson.getJSONObject("action").getJSONObject("clientExtra").optString("식사시간", "none");
        String foodNum = loadJson.getJSONObject("action").getJSONObject("clientExtra").optString("음식식별번호", "none");
        String foodName = loadJson.getJSONObject("action").getJSONObject("clientExtra").optString("음식명", "none");

        // 인분수와 곱해진 총중량,칼,탄,단,지들
        String weight = String.valueOf((int)(Integer.parseInt(loadJson.getJSONObject("action").getJSONObject("clientExtra").optString("기준", "none"))* integerPortion));
        String kcal = String.valueOf((int)(Integer.parseInt(loadJson.getJSONObject("action").getJSONObject("clientExtra").optString("칼로리", "none")) * integerPortion));
        String tan = String.valueOf((int)(Integer.parseInt(loadJson.getJSONObject("action").getJSONObject("clientExtra").optString("탄수화물", "none")) * integerPortion));
        String dan = String.valueOf((int)(Integer.parseInt(loadJson.getJSONObject("action").getJSONObject("clientExtra").optString("단백질", "none")) * integerPortion));
        String gi = String.valueOf((int)(Integer.parseInt(loadJson.getJSONObject("action").getJSONObject("clientExtra").optString("지방", "none")) * integerPortion));

        String predictrate = loadJson.getJSONObject("action").getJSONObject("clientExtra").optString("확률", "none");
        String candidate1 = loadJson.getJSONObject("action").getJSONObject("clientExtra").optString("후보1", "none");
        String candidate2 = loadJson.getJSONObject("action").getJSONObject("clientExtra").optString("후보2", "none");
        String candidate3 = loadJson.getJSONObject("action").getJSONObject("clientExtra").optString("후보3", "none");
        String candidate1rate = loadJson.getJSONObject("action").getJSONObject("clientExtra").optString("후보1확률", "none");
        String candidate2rate = loadJson.getJSONObject("action").getJSONObject("clientExtra").optString("후보2확률", "none");
        String candidate3rate = loadJson.getJSONObject("action").getJSONObject("clientExtra").optString("후보3확률", "none");

        // 오늘날짜
        Date nowDate = new SimpleDateFormat("yy/MM/dd").parse(new SimpleDateFormat("yy/MM/dd").format(new Date()));

        // 데이터 저장
        FoodUpVO vo = new FoodUpVO();
        vo.setUpphotoid(upphotoid); // 시퀀스
        vo.setNormalId(userName); // 노말유저아이디
        vo.setNnum(foodUpService.selectNnum(vo.getNormalId())); // 노말유저 식별번호
        vo.setCategory(mealTime); // 아점저간
        vo.setFoodnum(Integer.parseInt(foodNum)); // 음식식별번호
        vo.setUploaddate(nowDate); // 등록날짜
        vo.setPredictrate(Double.valueOf(predictrate)); // 확률
        vo.setCandidate1(Integer.parseInt(candidate1));
        vo.setCandidate2(Integer.parseInt(candidate2));
        vo.setCandidate3(Integer.parseInt(candidate3));
        vo.setCandidate1rate(Double.valueOf(candidate1rate));
        vo.setCandidate2rate(Double.valueOf(candidate2rate));
        vo.setCandidate3rate(Double.valueOf(candidate3rate));
        vo.setMass(Integer.parseInt(weight)); // 총 음식량

        //sql처리
        foodUpService.transactionInsertUpdate(vo);
        
        return Map.of(
            "version", "2.0",
            "template", Map.of(
                "outputs", new Object[]{
                    Map.of(
                        "simpleText", Map.of(
                            "text", "아래 내용을 기록합니다. \n\n" +
                                "식사시간 : " + mealTime + "\n\n" +
                                
                                "섭취량 : " + integerPortion + "인분\n" +
                                "음식이름 : " + foodName  + "\n" +
                                "총중량 : " + weight  + "(g)\n" +
                                "칼로리 : " + kcal + "kal\n" +
                                "탄수화물 : " + tan + "(g)\n" +
                                "단백질 : " + dan + "(g)\n" +
                                "지방 : " + gi + "(g)"
                        )
                    )
                }
            )
        );
    }

    // 다른 음식으로 등록할래요
    @RequestMapping("/please_other_meal")
    @ResponseBody
    public Map<String, Object> pleaseOtherMeal(@RequestBody String body){
        JSONObject loadJson = new JSONObject(body);

        String plusfriendUserKey = kakaoChatbotService.getPlusfriendUserKey(body);
        String userName = kakaoChatbotService.getUserNameByPlusfriendUserKey(plusfriendUserKey);

        // userName이 null이면 연동코드와 연결된 계정이 없습니다.
        if (userName == null) {
            return Map.of(
                "version", "2.0",
                "template", Map.of(
                    "outputs", new Object[]{
                        Map.of(
                            "simpleText", Map.of(
                                "text", "유효하지 않은 요청입니다."
                            )
                        )
                    }
                )
            );
        }

        String mealTime = loadJson.getJSONObject("action").getJSONObject("clientExtra").optString("식사시간", "none");
        String pictureUrl = loadJson.getJSONObject("action").getJSONObject("clientExtra").optString("받은사진", "none");

        // 후보들을 버튼으로.
        int candidate1 = Integer.parseInt(loadJson.getJSONObject("action").getJSONObject("clientExtra").optString("후보1", "none"));
        int candidate2 = Integer.parseInt(loadJson.getJSONObject("action").getJSONObject("clientExtra").optString("후보2", "none"));
        int candidate3 = Integer.parseInt(loadJson.getJSONObject("action").getJSONObject("clientExtra").optString("후보3", "none"));

        List<String> rateList = new ArrayList<>();
        String candidate1rate = loadJson.getJSONObject("action").getJSONObject("clientExtra").optString("후보1확률", "none");
        String candidate2rate = loadJson.getJSONObject("action").getJSONObject("clientExtra").optString("후보2확률", "none");
        String candidate3rate = loadJson.getJSONObject("action").getJSONObject("clientExtra").optString("후보3확률", "none");
        rateList.add(candidate1rate);
        rateList.add(candidate2rate);
        rateList.add(candidate3rate);

        List<FoodVO> foodVOList = new ArrayList<>();
        foodVOList.add(foodDao.selecOnetFood(candidate1));
        foodVOList.add(foodDao.selecOnetFood(candidate2));
        foodVOList.add(foodDao.selecOnetFood(candidate3));

        List<Map<String, Object>> buttonItems = new ArrayList<>();
        for(int i = 0; i < foodVOList.size(); i++) {
            Map<String, Object> item = Map.of(
                "action", "block",
                "label", foodVOList.get(i).getFOODNAME(),
                "messageText", "식단 기록하기",
                "extra", Map.ofEntries(
                    entry("userName", userName),
                    entry("식사시간", mealTime),
                    entry("받은사진", pictureUrl),
                    entry("음식식별번호", foodVOList.get(i).getFOODNUM()),
                    entry("음식명", foodVOList.get(i).getFOODNAME()),
                    entry("기준", foodVOList.get(i).getFOODWEIGHT()),
                    entry("칼로리", foodVOList.get(i).getFOODCAL()),
                    entry("탄수화물", foodVOList.get(i).getFOOD_TAN()),
                    entry("단백질", foodVOList.get(i).getFOOD_DAN()),
                    entry("지방", foodVOList.get(i).getFOOD_GI()),
                    entry("확률", rateList.get(i)),
                    entry("후보1", candidate1),
                    entry("후보2", candidate2),
                    entry("후보3", candidate3),
                    entry("후보1확률", candidate1rate),
                    entry("후보2확률", candidate2rate),
                    entry("후보3확률", candidate3rate)
                )
            );
            buttonItems.add(item);
        }

        return Map.of(
            "version", "2.0",
            "template", Map.of(
                "outputs", new Object[]{
                    Map.of(
                        "textCard", Map.of(
                            "description", "어떤 음식으로 등록할까요?",
                            "buttons", buttonItems
                        )
                    )
                }
            )
        );
    }
}
