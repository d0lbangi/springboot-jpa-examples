package com.example.jpa.board.controller;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.example.jpa.board.service.BoardService;
import com.example.jpa.common.model.ResponseResult;
import com.example.jpa.util.JWTUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RequiredArgsConstructor
@RestController
public class ApiBoardBookmarkController {


    private final BoardService boardService;

    /**
     * 77. 게시물의 북마크를 추가/삭제하는 API 기능 작성
     * */
    @PutMapping("/api/board/{id}/scrap")
    public ResponseEntity<?> boardBookMark(@PathVariable Long id
    , @RequestHeader("F-TOKEN") String token) {

        String email = "";
        try {
            email = JWTUtils.getIssuer(token);
        } catch (JWTVerificationException e) {
            return ResponseResult.fail("토큰 정보가 정확하지 않습니다.");
        }

        return ResponseResult.result(boardService.addBookmark(id, email));
    }

    @DeleteMapping("/api/scrap/{id}")
    public ResponseEntity<?> deleteBookmark(@PathVariable Long id
    , @RequestHeader("F-TOKEN") String token) {

        String email = "";
        try {
            email = JWTUtils.getIssuer(token);
        } catch (JWTVerificationException e) {
            return ResponseResult.fail("토큰 정보가 없습니다.");
        }

        return ResponseResult.result(boardService.removeBookmark(id, email));
    }

}



















