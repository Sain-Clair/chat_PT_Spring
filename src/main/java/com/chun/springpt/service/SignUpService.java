package com.chun.springpt.service;

import java.util.Base64;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.chun.springpt.dao.FoodDao;
import com.chun.springpt.dao.SignUpDao;
import com.chun.springpt.vo.FoodVO;

@Service
public class SignUpService {
    @Autowired
    private SignUpDao sdao;
    @Autowired
    private FoodDao fdao;
    @Autowired
    private S3uploadService s3uploadService;
    
    // 일반 회원가입
    @Transactional
    public int insertMembers(Map<String, Object> data) {
        try {
            String imgbase64 = (String) data.get("nm_profileimg");
            if (imgbase64 instanceof String) {
                System.out.println("타입: String");
            } else {
                System.out.println("타입: " + imgbase64.getClass().getName());
            }
            byte[] imageBytes = Base64.getDecoder().decode(imgbase64.split(",")[1]);
            int insertMemResult = sdao.insertMembers(data);
            int insertNormalResult = sdao.insertNormal(data);
            int nnum = (int) data.get("nnum");
            data.put("nnum", nnum); // nnum 값을 data에 삽입
            int insertMemFoodResult = sdao.insertMemFood(data);
            int sum = insertMemResult + insertNormalResult + insertMemFoodResult;
            String filePath = "normal_user/" + nnum + ".png";
            s3uploadService.saveFilewithName(filePath, imageBytes);

            if (sum > 3) {
                return 1;
            } else {
                return 0;
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public int select_trainer_mem_seq(){
        return sdao.select_trainer_mem_seq();
    }

    // PT 회원가입
    @Transactional
    public int insertTrainerMembers(Map<String, Object> data) {
        try {


            // awards의 타입을 확인하고 처리
            List<String> awardsList = (List<String>) data.get("awards");
            int i = 1;
            for(String awards : awardsList){
                data.put("awards"+i, awards);
                i++;
            }

            int insertMemResult = sdao.insertMembers(data);

            int insertTrainerResult = sdao.insertTrainer(data);
            int seqNum = select_trainer_mem_seq();
            int sum = insertMemResult + insertTrainerResult;
            System.out.println("트레이너 회원가입 sum:" + sum);

            // 이미지 처리
            List<Object> imgbase64List = (List<Object>) data.get("imgs");
            i = 0;
            for (Object imgbase64Object : imgbase64List) {

                String imgbase64 = imgbase64Object.toString();
                byte[] imageBytes = Base64.getDecoder().decode(imgbase64.split(",")[1]);

                // tnum 값 처리
                String imgName = seqNum + "_" + i;

                String mainfilePath = "trainer/profile_img/" + imgName + ".png";
                s3uploadService.saveFilewithName(mainfilePath, imageBytes);
                i++;
            }




            return sum >= 2 ? 1 : 0;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    // 이미 존재하는 이메일인지 확인
    public int validCheckEmail(String email) {
        // 0이면 가입가능, 1이면 불가능
        int checkEmail = sdao.validCheckEmail(email);
        System.out.println("서비스" + checkEmail);
        return checkEmail;
    }

    public int validCheckId(String id) {
        int checkId = sdao.validCheckId(id);
        return checkId;
    }

    // 음식 리스트 가져오기
    public List<FoodVO> selectFoodList() {
        return fdao.selectFoodList();
    }

}
