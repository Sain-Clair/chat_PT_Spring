package com.chun.springpt.security.config;

import com.chun.springpt.security.filter.JwtTokenFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

//@EnableWebSecurity: Spring Security 설정을 활성
@EnableWebSecurity
@Configuration
public class SecurityConfig {
  // JWT 토큰을 검증하는 필터
  @Autowired
  private JwtTokenFilter jwtAuthenticationFilter;

  @Autowired
  private UserDetailsService userDetailsService;

  // 이 메소드는 DaoAuthenticationProvider 객체를 생성하고 구성
  // UserDetailsService와 PasswordEncoder를 설정하여 사용자 인증 정보를 관리한다.
  @Bean
  public DaoAuthenticationProvider daoAuthenticationProvider() {
    DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
    provider.setUserDetailsService(userDetailsService);
    provider.setPasswordEncoder(passwordEncoder());
    return provider;
  }

  // public SecurityConfig(JwtTokenFilter jwtAuthenticationFilter,
  // UserDetailsService userDetailsService,
  // DaoAuthenticationProvider daoAuthenticationProvider) {
  // this.jwtAuthenticationFilter = jwtAuthenticationFilter;
  // this.userDetailsService = userDetailsService;
  // this.daoAuthenticationProvider = daoAuthenticationProvider;
  // }

  // @Bean
  // public AuthenticationProvider authenticationProvider() {
  // daoAuthenticationProvider.setUserDetailsService(userDetailsService);
  // daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
  // return daoAuthenticationProvider;
  // }

  // @Bean
  // PasswordEncoder passwordEncoder() {
  // return NoOpPasswordEncoder.getInstance();
  // }
  // BCryptPasswordEncoder
  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  AuthenticationManager authenticationManager(
      AuthenticationConfiguration authenticationConfiguration) throws Exception {
    return authenticationConfiguration.getAuthenticationManager();
  }
  // *****
  // csrf((csrf) -> csrf.disable()): CSRF(Cross-Site Request Forgery) 보호 기능을 비활성.
  // REST API와 같이 세션을 사용하지 않는 경우에 일반적으로 CSRF 보호를 비활성함
  // .formLogin((login) -> login.disable()) //폼 기반 로그인과 HTTP 기본 인증을 비활성화
  // . JWT 토큰 기반 인증을 사용하는 경우 일반적
  // .authorizeHttpRequests((auth) -> auth.anyRequest().permitAll()) , 모든 요청에 대해
  // 접근을 허용
  // .sessionManagement((session) ->
  // session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
  // ->세션을 사용하지 않고, 상태를 유지하지 않도록 설정합니다. JWT 토큰 기반 인증에서 일반적
  // logout((logout) -> logout.disable()): 로그아웃 기능을 비활성, AJax,Axios 비동기 방식및
  // Vue,React방식에서
  // 로그인 상태를 XXController모델에서 체크 하지 않음

  // 요청이 들어 올때 /api/auth/** 패턴에 해당하는 모든 경로에 대해 보안 요구 사항을 적용하지 않도록 설정하고
  // 그 외의 모든 요청에 대해서는 인증을 요구
  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http
        .csrf((csrf) -> csrf.disable())
        .cors((cors) -> cors.configurationSource(corsConfigurationSource()))
        .formLogin((login) -> login.disable())
        .httpBasic((basic) -> basic.disable())
        // HTTP 요청에 대한 보안 필터 체인을 구성
        // .authorizeHttpRequests((auth) -> auth.anyRequest().permitAll())
        .authorizeHttpRequests((auth) -> auth
            .requestMatchers("/api/auth/**").permitAll()
            .anyRequest().authenticated())
        // JWT 토큰 필터 추가: JwtTokenFilter를 BasicAuthenticationFilter 전에 추가하여 JWT 토큰을 검증
        .addFilterBefore(jwtAuthenticationFilter, BasicAuthenticationFilter.class)
        // 세션 정책 설정: SessionCreationPolicy.STATELESS로 설정하여 세션 기반 인증을 사용하지 않도록 한다.
        .sessionManagement((session) -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        // 로그아웃 기능 비활성화: 상태를 유지하지 않는 인증 방식에서는 로그아웃이 필요 없다.
        .logout((logout) -> logout.disable());
    return http.build();
  }

  @Bean
  public CorsConfigurationSource corsConfigurationSource() {
    CorsConfiguration corsConfiguration = new CorsConfiguration();
    corsConfiguration.setAllowCredentials(true);
    corsConfiguration.setAllowedOrigins(List.of("http://localhost:8081/", "http://192.168.0.52:8081/"));
    corsConfiguration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS"));
    corsConfiguration.setAllowedHeaders(List.of("*"));
    UrlBasedCorsConfigurationSource urlBasedCorsConfigurationSource = new UrlBasedCorsConfigurationSource();
    urlBasedCorsConfigurationSource.registerCorsConfiguration("/**", corsConfiguration);

    return urlBasedCorsConfigurationSource;
  }
}