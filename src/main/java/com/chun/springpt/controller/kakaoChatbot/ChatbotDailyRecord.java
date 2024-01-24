package com.chun.springpt.controller.kakaoChatbot;

import com.chun.springpt.service.DietService;
import com.chun.springpt.service.kakaoChatbot.KakaoChatbotService;
import com.chun.springpt.vo.DailyTotalVO;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;

@RestController
@Slf4j
@EnableAsync
@SuppressWarnings("unchecked")
@RequestMapping(value = "/chatbot")
public class ChatbotDailyRecord {

    @Autowired
    private KakaoChatbotService kakaoChatbotService;

    @Autowired
    private DietService Dservice;

    @RequestMapping("/daily_record")
    @ResponseBody
    public Map<String, Object> recordWeight(@RequestBody String body) throws ParseException {
        JSONObject loadJson = new JSONObject(body);

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

        Map<String, Object> recommandTandnagi = Dservice.GetRecommandTandangi(userName);
        DailyTotalVO dailytotal = Dservice.getTotaldailyinfo(userName);

        // 권장이들
        int recommandCal = (int)Double.parseDouble(recommandTandnagi.get("p_recommand_cal").toString());
        int recommandTan = (int)Double.parseDouble(recommandTandnagi.get("p_recommand_tan").toString());
        int recommandDan = (int)Double.parseDouble(recommandTandnagi.get("p_recommand_dan").toString());
        int recommandGi = (int)Double.parseDouble(recommandTandnagi.get("p_recommand_gi").toString());

        // 토탈데일리들
        int totalCal = (int)dailytotal.getDailyTotalCal();
        int totalTan = (int)dailytotal.getDailyTotalTan();
        int totalDan = (int)dailytotal.getDailyTotalDan();
        int totalGi = (int)dailytotal.getDailyTotalGi();

        // 오늘 날짜
        SimpleDateFormat dateFormat = new SimpleDateFormat("M월 d일", Locale.KOREAN);
        String formattedDate = dateFormat.format(new Date());

        return Map.of(
            "version", "2.0",
            "template", Map.of(
                "outputs", List.of(
                    Map.of(
                        "itemCard", Map.of(
                            "imageTitle", Map.of(
                                "title", formattedDate,
                                "description", "영양 정보 요약"
                            ),
                            "title", "",
                            "description", "",
                            "thumbnail", Map.of(
                                "imageUrl", "https://chat-pt.s3.ap-northeast-2.amazonaws.com/etc/main_mobile.png",
                                "width", 800,
                                "height", 800
                            ),
                            "itemList", List.of(
                                Map.of(
                                    "title", "칼로리",
                                    "description", totalCal + " / " + recommandCal + " kcal"
                                ),
                                Map.of(
                                    "title", ".",
                                    "description", String.format("%.0f%%", ((double)totalCal / recommandCal) * 100 )
                                ),
                                Map.of(
                                    "title", "탄수화물",
                                    "description",  totalTan + " / " + recommandTan + " (g)"
                                ),
                                Map.of(
                                    "title", ".",
                                    "description", String.format("%.0f%%", ((double)totalTan / recommandTan) * 100)
                                ),
                                Map.of(
                                    "title", "단백질",
                                    "description", totalDan + " / " + recommandDan + " (g)"
                                ),
                                Map.of(
                                    "title", ".",
                                    "description", String.format("%.0f%%", ((double)totalDan / recommandDan) * 100)
                                ),
                                Map.of(
                                    "title", "지방",
                                    "description", totalGi + " / " + recommandGi + " (g)"
                                ),
                                Map.of(
                                    "title", ".",
                                    "description", String.format("%.0f%%", ((double)totalGi / recommandGi) * 100)
                                )
                            ),
                            "itemListAlignment", "right",
                            "buttonLayout", "vertical"
                        )
                    )
                )
            )
        );
    }

}
