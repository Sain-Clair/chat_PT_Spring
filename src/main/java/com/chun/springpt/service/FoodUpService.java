package com.chun.springpt.service;

import com.chun.springpt.dao.FoodUpDao;
import com.chun.springpt.vo.FoodUpVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.*;

@Service
@Slf4j
public class FoodUpService {
    @Autowired
    private FoodUpDao foodUpDao;

    public int getNextUpPhotoId() {
        return foodUpDao.getNextUpPhotoId();
    }
    public void insertFoodData(FoodUpVO vo){
        foodUpDao.insertFoodData(vo);
    }
    public void insertUpPhoto(FoodUpVO vo){
        foodUpDao.insertUpPhoto(vo);
    }
    public List<Map<String,Object>> selectRatingDate(int nnum){
        return foodUpDao.selectRatingDate(nnum);
    }
    public void insertMemberFood(Map<String,Object> paramMap){
        foodUpDao.insertMemberFood(paramMap);
    }
    public void updateMemberFood(Map<String,Object> paramMap){
        foodUpDao.updateMemberFood(paramMap);
    }

    public int selectNnum(String normalId) {
        return foodUpDao.selectNnum(normalId);
    }

    public void deleteMemberFood(int upphotoid) {
        foodUpDao.deleteMemberFood(upphotoid);
    }

    public void updateSubtractFood(Date currentDate) {
        foodUpDao.updateSubtractFood(currentDate);
    }

    @Transactional
    public void transactionInsertUpdate(FoodUpVO vo){
        log.info("transactionInsertUpdate 실행");

        // DB 작업 시작
        insertUpPhoto(vo);

        int nnum = vo.getNnum();
        Date inputDate = removeTime(vo.getUploaddate());
        List<Map<String, Object>> ratingList = selectRatingDate(nnum);

        int newerDatesCount = calculateNewerDatesCount(ratingList, inputDate);
        int newRating = 10 - newerDatesCount;

        Map<String, Object> mfMap = createMemberFoodMap(vo, newRating);
        insertMemberFood(mfMap);

        if (isNewData(newerDatesCount)) {
            updateSubtractFood(inputDate);
            deleteMemberFood(nnum);
        }
    }
    private Date removeTime(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }
    private int calculateNewerDatesCount(List<Map<String, Object>> ratingList, Date inputDate) {
        int newerDatesCount = 0;
        Set<String> countedDates = new HashSet<>();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yy-MM-dd");

        for (Map<String, Object> rating : ratingList) {
            Date existingDate = removeTime((Date) rating.get("UPLOADDATE"));
            String dateString = dateFormat.format(existingDate);

            if (existingDate.after(inputDate) && !countedDates.contains(dateString)){
                newerDatesCount++;
                countedDates.add(dateString);
            }
        }
        return newerDatesCount;
    }

    private Map<String, Object> createMemberFoodMap(FoodUpVO vo, int newRating) {
        Map<String, Object> memberFoodMap = new HashMap<>();
        memberFoodMap.put("foodnum", vo.getFoodnum());
        memberFoodMap.put("nnum", vo.getNnum());
        memberFoodMap.put("upphotoid", vo.getUpphotoid());
        memberFoodMap.put("rating", newRating);
        return memberFoodMap;
    }

    private boolean isNewData(int newerDatesCount) {
        // 새로운 데이터가 있으면 true, 없으면 false 반환
        return newerDatesCount == 0;
    }


}
