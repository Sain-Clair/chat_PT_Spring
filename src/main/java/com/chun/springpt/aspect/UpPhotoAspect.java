//package com.chun.springpt.aspect;
//
//
//import org.aspectj.lang.ProceedingJoinPoint;
//import org.aspectj.lang.annotation.Around;
//import org.aspectj.lang.annotation.Aspect;
//import org.springframework.stereotype.Component;
//
//@Aspect
//@Component
//public class UpPhotoAspect {
//
//    @Around("execution(* com.chun.springpt.controller.UpPhotoController.deleteFood(..))")
//    public Object aroundDeleteFood(ProceedingJoinPoint joinPoint) throws Throwable {
//        try {
//            // 실행 전 로직 (예: 로깅, 보안 검사)
//            // 원래 메소드 실행
//            Object result = joinPoint.proceed();
//            // 실행 후 로직 (예: 추가 로깅, 리소스 정리)
//            return result;
//        } catch (Throwable e) {
//            // 예외 처리 로직
//            throw e;
//        }
//    }
//}
