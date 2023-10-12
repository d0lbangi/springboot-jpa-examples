package com.example.jpa.notice.controller;


import com.example.jpa.notice.model.NoticeModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RestController
public class ApiNoticeController {
    /*
    @GetMapping("/api/notice")
    public String noticeString() {
        return "공지사항입니다.";
    }
    */

    /*
    @GetMapping("/api/notice")
    public NoticeModel notice() {

        LocalDateTime regDate = LocalDateTime.of(2021, 2, 8, 0,0);

        NoticeModel notice = new NoticeModel();
        notice.setId(1);
        notice.setTitle("공지사항 입니다.");
        notice.setContents("공지사항 내용입니다.");
        notice.setRegDate(regDate);

        return notice;
    }
     */


/*    @GetMapping("/api/notice")
    public List<NoticeModel> notice() {

        List<NoticeModel> noticeList = new ArrayList<>();

        noticeList.add(NoticeModel.builder()
                .id(1)
                .title("공지사항입니다.")
                .contents("공지사항내용입니다.")
                .regDate(LocalDateTime.of(2021, 1, 30, 0, 0))
                .build());

        noticeList.add(NoticeModel.builder()
                .id(2)
                .title("두번째 공지사항입니다.")
                .contents("두번째 공지사항내용입니다.")
                .regDate(LocalDateTime.of(2021, 1, 31, 0, 0))
                .build());

        return noticeList;
    }*/

/* 9. 공지사항 게시판의 목록에 대한 요청을 처리하는 API를 만들어 보자.
    @GetMapping("/api/notice")
    public List<NoticeModel> notice() {

        List<NoticeModel> noticeList = new ArrayList<>();
        return noticeList;
    }*/

/*    10. "공지사항 게시판의 목록 중 전체 개수 정보에 대한 요청을 처리하는 API를 만들어보자."
    [조건]
    - REST API 형식으로 구현
    - HTTP METHOD 는 GET
    - 요청 주소는 ""/api/notice/count""
    - 리턴값은 게시판의 게시글 개수(정수)를 리턴
    - [확인사항]
    컨트롤러에서 정수형을 리턴하였더라도 클라이언트쪽에 내려가는 부분은 문자열임
    @GetMapping("/api/notice/count")
    public int noticeCount() {
        return 10;
    }*/

/*    11. "공지사항에 글을 등록하기 위해서 글작성에 대한 API를 만들어 보세요."
    [조건]
    - REST API 형식으로 구현
    - HTTP METHOD 는 POST
    - 요청 주소는 ""/api/notice""
    - 전달되는 파라미터는 x-www-form-urlencoded 형식의 제목, 내용을 입력 받음
    - 파라미터는 추상화하지 않고 기본데이터 타입 형태로 전달받음
    - 리턴값은 입력된 형태에 게시글ID(1)를 추가하여 모델 형태로 리턴
    @PostMapping("/api/notice")
    public NoticeModel addNotice(String title, String contents) {

        NoticeModel notice = NoticeModel.builder()
                .id(1)
                .title(title)
                .contents(contents)
                .regDate(LocalDateTime.now())
                .build();

        return notice;
    }*/


/*    12. "공지사항에 글을 등록하기 위해서 글작성에 대한 API 만들기"
    [조건]
    - REST API 형식으로 구현
    - HTTP METHOD 는 POST
    - 요청 주소는 ""/api/notice2""
    - 전달되는 파라미터는 x-www-form-urlencoded 형식의 제목, 내용을 입력 받음
    - 파라미터를 공지사항 모델로 추상화하여 전달받음
    - 리턴값은 입력된 형태에 게시글ID(2)과 등록일자(현재시간)을 추가하여 모델 형태로 리턴
    @PostMapping("/api/notice2")
    public NoticeModel addNotice(NoticeModel noticeModel) {

        noticeModel.setId(2);
        noticeModel.setRegDate(LocalDateTime.now());

        return noticeModel;
    }*/
}
