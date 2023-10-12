package com.example.jpa.notice.controller;


import com.example.jpa.notice.model.NoticeModel;
import org.springframework.web.bind.annotation.GetMapping;
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

    // 9. 공지사항 게시판의 목록에 대한 요청을 처리하는 API를 만들어 보자.
    @GetMapping("/api/notice")
    public List<NoticeModel> notice() {

        List<NoticeModel> noticeList = new ArrayList<>();
        return noticeList;
    }

/*    10. "공지사항 게시판의 목록 중 전체 개수 정보에 대한 요청을 처리하는 API를 만들어보자."
    [조건]
    - REST API 형식으로 구현
    - HTTP METHOD 는 GET
    - 요청 주소는 ""/api/notice/count""
    - 리턴값은 게시판의 게시글 개수(정수)를 리턴
    - [확인사항]
    컨트롤러에서 정수형을 리턴하였더라도 클라이언트쪽에 내려가는 부분은 문자열임*/
    @GetMapping("/api/notice/count")
    public int noticeCount() {
        return 10;
    }
}
