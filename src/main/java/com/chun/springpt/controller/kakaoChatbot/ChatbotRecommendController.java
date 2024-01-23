package com.chun.springpt.controller.kakaoChatbot;

import com.chun.springpt.service.kakaoChatbot.KakaoChatbotService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@Slf4j
@RequestMapping(value = "/chatbot")
public class ChatbotRecommendController {

    @Autowired
    private KakaoChatbotService kakaoChatbotService;

    @RequestMapping(value = "/recommend_menu")
    @ResponseBody
    @SuppressWarnings("unchecked")
    public Map<String, Object> recommendMenu(@RequestBody String body) {

        String plusfriendUserKey = kakaoChatbotService.getPlusfriendUserKey(body);
        String userName = kakaoChatbotService.getUserNameByPlusfriendUserKey(plusfriendUserKey);
        String nickname = kakaoChatbotService.getNicknameByPlusfriendUserKey(plusfriendUserKey);

        // 만약 일반회원이 아니거나, 연동코드로 일반회원의 정보를 가져오지 못했다면,
        if (nickname == null) {
            return Map.of(
                "version", "2.0",
                "template", Map.of(
                    "outputs", List.of(
                        Map.of(
                            "textCard", Map.of(
                                "title", "연동코드와 연결된 계정이 없습니다.",
                                "description", "코드를 회원가입시 또는 회원정보수정 페이지에서 입력해주세요.\n",
                                "buttons", List.of(
                                    Map.of(
                                        "action", "webLink",
                                        "label", "회원가입",
                                        "webLinkUrl", "http://www.chatpt.shop/signUp/sign_up_main"
                                    )
                                )
                            )
                        ),
                        Map.of(
                            "simpleText", Map.of(
                                "text", plusfriendUserKey
                            )
                        )
                    )
                )
            );
        }

        // 연동된 회원임이 확인되면 식단 추천을 해준다.
        // 장고로부터 데이터 가져오기
        String url = kakaoChatbotService.djangoBaseUrl + "/dlmodel/getCal?id=" + userName;
        RestTemplate restTemplate = new RestTemplate();
        Map<String, Object> response = restTemplate.getForObject(url, Map.class);

        //// 하루 식단을 분석한 결과
        // 칼로리
        String calorie = String.valueOf(Math.round((Double.parseDouble(response.get("now_cal").toString()) / Double.parseDouble(response.get("recommand_cal").toString()))*100));
        String tan = String.valueOf(Math.round((((List<Double>)response.get("now_nutrition")).get(0) / ((List<Double>)response.get("recomand_nutrition")).get(0))*100));
        String dan = String.valueOf(Math.round((((List<Double>)response.get("now_nutrition")).get(1) / ((List<Double>)response.get("recomand_nutrition")).get(1))*100));
        String gi = String.valueOf(Math.round((((List<Double>)response.get("now_nutrition")).get(2) / ((List<Double>)response.get("recomand_nutrition")).get(2))*100));

        // 캐루셀 5개
        List<Map<String, Object>> carouselItems = new ArrayList<>();
        // 음식 리스트
        List<Map<String, Object>> foodlist = (List<Map<String, Object>>)response.get("recomandfood");
        for (int i = 0; i < 5; i++) {
            Map<String, Object> item = Map.of(
                "imageTitle", Map.of(
                    "title", foodlist.get(i).get("FOODNAME").toString(),
                    "description", "기준" +  foodlist.get(i).get("FOODWEIGHT").toString() + "(g)"
                ),
                "title", "",
                "description", "",
                "thumbnail", Map.of(
                    "imageUrl", kakaoChatbotService.s3ImageUrl + "/food_main_images/" + foodlist.get(i).get("FOODIMG").toString(),
                    "width", "800",
                    "height", "800"
                ),
                "itemList", List.of(
                    Map.of("title", "칼로리", "description", foodlist.get(i).get("FOODCAL").toString() + "kcal"),
                    Map.of("title", "탄수화물", "description", foodlist.get(i).get("FOOD_TAN").toString() + "(g)"),
                    Map.of("title", "단백질", "description", foodlist.get(i).get("FOOD_DAN").toString() + "(g)"),
                    Map.of("title", "지방", "description", foodlist.get(i).get("FOOD_GI").toString() + "(g)")
                )
            );
            carouselItems.add(item);
        }

        return Map.of(
            "version", "2.0",
            "template", Map.of(
                "outputs", List.of(
                    Map.of(
                        "simpleText", Map.of(
                            "text", nickname + "님의 하루 식단을 분석한 결과" +
                                " 하루 권장 섭취량 기준, \n\n" +
                                "칼로리 : " + calorie + "%\n" +
                                "탄수화물 : " + tan + "%\n" +
                                "단백질 : " + dan + "%\n" +
                                "지방 : " + gi + "%\n\n" +
                                "섭취하셨습니다."
                        )
                    ),
                    Map.of(
                        "simpleText", Map.of(
                            "text", "아래 식단 추천을 해드릴게요!"
                        )
                    ),
                    Map.of(
                        "carousel", Map.of(
                            "type", "itemCard",
                            // 캐루셀 5개
                            "items", carouselItems,
                            "itemListAlignment", "right"
                        )
                    )
                )
            )
        );

    }
}
