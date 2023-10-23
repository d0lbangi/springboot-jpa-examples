package com.example.jpa.extra.controller;

import com.example.jpa.common.model.ResponseResult;
import com.example.jpa.common.properties.NaverAppProperties;
import com.example.jpa.extra.model.KakaoTranslateInput;
import com.example.jpa.extra.model.NaverTranslateInput;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;


@Slf4j
@RequiredArgsConstructor
@RestController
public class ApiExtraNaverController {

    private final NaverAppProperties naverAppProperties;


    /**
     * 91. Naver OPEN API를 활용한 게시글 번역서비스를 구현하는 API 작성
     */
/*    @GetMapping("/api/extra/kakao/translate")
    public ResponseEntity<?> translate(@RequestBody NaverTranslateInput naverTranslateInput){

        *//*
        POST /v1/papago/n2mt HTTP/1.1
        HOST: openapi.naver.com
        User-Agent: curl/7.49.1
        Accept:
        Content-Type: application/x-www-form-urlencoded; charset=UTF-8
        X-Naver-Client-Id: {애플리케이션 등록 시 발급받은 클라이언트 아이디 값}
        X-Naver-Client-Secret: {애플리케이션 등록 시 발급받은 클라이언트 시크릿 값}
        Content-Length: 51
        *//*

        String ClientId = "iOCPedx2INBZNHzYILLM";
        String ClientSecret = "{ClientSecret}";
        String url = "https://openapi.naver.com/v1/papago/n2mt";

        RestTemplate restTemplate = new RestTemplate();

        MultiValueMap<String, String> parameters = new LinkedMultiValueMap<>();
        parameters.add("source", "kr");
        parameters.add("target", "en");
        parameters.add("text", naverTranslateInput.getText());

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.add("X-Naver-Client-Id", ClientId);
        headers.add("X-Naver-Client-Secret", ClientSecret);

        HttpEntity formEntity = new HttpEntity(parameters, headers);
        ResponseEntity<String> responseEntity = restTemplate.postForEntity(url, formEntity, String.class);

        return ResponseResult.success(responseEntity.getBody());
    }*/


    /**
     * 94. 91번 문제의 OPEN API연동 문제에서 활용한 API키를 프로퍼티로 설정하여 호출하는 API 작성
     */
    @GetMapping("/api/extra/kakao/translate")
    public ResponseEntity<?> translate(@RequestBody NaverTranslateInput naverTranslateInput){

        /*
        POST /v1/papago/n2mt HTTP/1.1
        HOST: openapi.naver.com
        User-Agent: curl/7.49.1
        Accept:
        Content-Type: application/x-www-form-urlencoded; charset=UTF-8
        X-Naver-Client-Id: {애플리케이션 등록 시 발급받은 클라이언트 아이디 값}
        X-Naver-Client-Secret: {애플리케이션 등록 시 발급받은 클라이언트 시크릿 값}
        Content-Length: 51
        */

        String ClientId = naverAppProperties.getClientId();
        String ClientSecret = naverAppProperties.getClientSecret();
        String url = "https://openapi.naver.com/v1/papago/n2mt";

        RestTemplate restTemplate = new RestTemplate();

        MultiValueMap<String, String> parameters = new LinkedMultiValueMap<>();
        parameters.add("source", "kr");
        parameters.add("target", "en");
        parameters.add("text", naverTranslateInput.getText());

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.add("X-Naver-Client-Id", ClientId);
        headers.add("X-Naver-Client-Secret", ClientSecret);

        HttpEntity formEntity = new HttpEntity(parameters, headers);
        ResponseEntity<String> responseEntity = restTemplate.postForEntity(url, formEntity, String.class);

        return ResponseResult.success(responseEntity.getBody());
    }



}




























