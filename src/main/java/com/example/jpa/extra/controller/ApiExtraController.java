package com.example.jpa.extra.controller;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.example.jpa.board.entity.BoardType;
import com.example.jpa.board.model.*;
import com.example.jpa.board.service.BoardService;
import com.example.jpa.common.model.ResponseResult;
import com.example.jpa.extra.model.AirInput;
import com.example.jpa.extra.model.OpenApiResult;
import com.example.jpa.extra.model.PharmacySearch;
import com.example.jpa.notice.model.ResponseError;
import com.example.jpa.user.model.ResponseMessage;
import com.example.jpa.util.JWTUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.engine.spi.CollectionKey;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.util.Collection;
import java.util.Collections;
import java.util.List;


@Slf4j
@RequiredArgsConstructor
@RestController
public class ApiExtraController {

    /**
     * 86. RestTemplate을 이용한 공공데이터토털의 공공API 연동하여 전국약국목록을 가져오는 API 작성
     * - 공공데이터포털 주소: https://www.data.go.kr/
     * - 회원가입후 이용가능
     * - 전국 약국 정보 조회 서비스 키워드 검색이후 활용신청후 조회가능
     */
/*    @GetMapping("/api/extra/pharmacy")
    public String pharmacy() {

    String apiKey = "b%2B%2B4yXYfkmTA4JAcOcr3Gsx4qvS4i9hJCloSX4w6Nq0s2YyJ3a9V6OTUuBq%2B%2F5GMBNnLh69XyuFUmYFsXPtH7Q%3D%3D";
    String url = "https://apis.data.go.kr/B552657/ErmctInsttInfoInqireService/getParmacyFullDown?serviceKey=%s&pageNo=1&numOfRows=10";

    String apiResult = "";

    try {
        URI uri = new URI(String.format(url, apiKey));

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

        String result = restTemplate.getForObject(uri, String.class);

        apiResult = result;

    } catch (Exception e) {
        e.printStackTrace();
    }
    return apiResult;
    }*/

    /**
     * 87. RestTemplate을 이용한 공공데이터토털의 공공API 연동하여 전국약국목록을 가져오는 API 작성
     * - 공공데이터포털 주소: https://www.data.go.kr/
     * - 회원가입후 이용가능
     * - 전국 약국 정보 조회 서비스 키워드 검색이후 활용신청후 조회가능
     * - 결과 데이터를 모델로 매핑하여 처리
     */
    /*
    @GetMapping("/api/extra/pharmacy")
    public ResponseEntity<?> pharmacy() {

        String apiKey = "b%2B%2B4yXYfkmTA4JAcOcr3Gsx4qvS4i9hJCloSX4w6Nq0s2YyJ3a9V6OTUuBq%2B%2F5GMBNnLh69XyuFUmYFsXPtH7Q%3D%3D";
        String url = "https://apis.data.go.kr/B552657/ErmctInsttInfoInqireService/getParmacyFullDown?serviceKey=%s&pageNo=1&numOfRows=10";

        String apiResult = "";

        try {
            URI uri = new URI(String.format(url, apiKey));

            RestTemplate restTemplate = new RestTemplate();
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

            String result = restTemplate.getForObject(uri, String.class);

            apiResult = result;

        } catch (Exception e) {
            e.printStackTrace();
        }

        OpenApiResult jsonResult = null;
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            jsonResult = objectMapper.readValue(apiResult, OpenApiResult.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return ResponseResult.success(jsonResult);
    }
    */

    /**
     * 88. RestTemplate을 이용한 공공데이터토털의 공공API 연동하여 전국약국목록을 가져오는 API 작성
     * - 공공데이터포털 주소: https://www.data.go.kr/
     * - 회원가입후 이용가능
     * - 전국 약국 정보 조회 서비스 키워드 검색이후 활용신청후 조회가능
     * - 시도/군구 단위 검색기능에 대한 구현을 주기
     * - 결과 데이터를 모델로 매핑하여 처리
     */
    /*
    @GetMapping("/api/extra/pharmacy")
    public ResponseEntity<?> pharmacy(@RequestBody PharmacySearch pharmacySearch) {

        String apiKey = "b%2B%2B4yXYfkmTA4JAcOcr3Gsx4qvS4i9hJCloSX4w6Nq0s2YyJ3a9V6OTUuBq%2B%2F5GMBNnLh69XyuFUmYFsXPtH7Q%3D%3D";
        String url = String.format("https://apis.data.go.kr/B552657/ErmctInsttInfoInqireService/getParmacyFullDown?serviceKey=%s&pageNo=1&numOfRows=10", apiKey);

        String apiResult = "";

        try {
            url += String.format("&Q0=%s&Q1=%s"
                    , URLEncoder.encode(pharmacySearch.getSearchSido(), "UTF-8")
                    , URLEncoder.encode(pharmacySearch.getSearchGugun(), "UTF-8"));

            URI uri = new URI(url);

            RestTemplate restTemplate = new RestTemplate();
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

            String result = restTemplate.getForObject(uri, String.class);

            apiResult = result;

        } catch (Exception e) {
            e.printStackTrace();
        }

        OpenApiResult jsonResult = null;
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            jsonResult = objectMapper.readValue(apiResult, OpenApiResult.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return ResponseResult.success(jsonResult);
    }
    */

    /**
     * 89. 미세먼지 정보 조회(공공 API)를 통해서 내용을 내리는 API 작성
     */
    @GetMapping("/api/extra/air")
    public String air(@RequestBody AirInput airInput) {

    String apiKey = "";
    String url = "";

    String apiResult = "";

    try {
        URI uri = new URI(String.format(url, apiKey, URLEncoder.encode(airInput.getSearchSido(), "UTF-8")));

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

        String result = restTemplate.getForObject(uri, String.class);

        apiResult = result;

    } catch (Exception e) {
        e.printStackTrace();
    }
    return apiResult;
    }
}



















