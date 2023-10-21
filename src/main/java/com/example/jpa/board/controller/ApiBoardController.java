package com.example.jpa.board.controller;

import com.example.jpa.board.entity.BoardType;
import com.example.jpa.board.model.BoardTypeInput;
import com.example.jpa.board.model.ServiceResult;
import com.example.jpa.board.service.BoardService;
import com.example.jpa.notice.model.ResponseError;
import com.example.jpa.user.model.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;

@RequiredArgsConstructor
@RestController
public class ApiBoardController {


    private final BoardService boardService;

   /**
    * 61. 게시판 타입을 추가하는 API
    *  - 동일한 게시판 제목이 있는 경우 status:200, result: false, message에 이미 동일한 게시판이 존재한다는 메시지 리턴
    *  - 게시판이름은 필수항목에 대한 부분 체크
    * */

   @PostMapping("/api/board/type")
   public ResponseEntity<?> addBoardType(@RequestBody @Valid BoardTypeInput boardTypeInput, Errors errors) {

       if(errors.hasErrors()) {
           List<ResponseError> responseErrors = ResponseError.of(errors.getAllErrors());
           return new ResponseEntity<>(ResponseMessage.fail("입력값이 정확하지 않습니다.", responseErrors), HttpStatus.BAD_REQUEST);
       }

       ServiceResult result = boardService.addBoard(boardTypeInput);

       if(!result.isResult()) {
           return ResponseEntity.ok().body(ResponseMessage.fail(result.getMessage()));
       }
       return ResponseEntity.ok().build();
   }


   /**
    * 62. 게시판 타입명을 수정하는 API 작성
    * - 게시판명이 동일한 경우 "수정할 이름이 동일한 게시판명입니다."
    * */
   @PutMapping("/api/board/type/{id}")
   public ResponseEntity<?> updateBoardType(@PathVariable Long id, @RequestBody @Valid BoardTypeInput boardTypeInput, Errors errors) {

       if(errors.hasErrors()) {
           List<ResponseError> responseErrors = ResponseError.of(errors.getAllErrors());
           return new ResponseEntity<>(ResponseMessage.fail("입력값이 정확하지 않습니다.", responseErrors), HttpStatus.BAD_REQUEST);
       }

       ServiceResult result = boardService.updateBoard(id, boardTypeInput);

       if(!result.isResult()) {
           return ResponseEntity.ok().body(ResponseMessage.fail(result.getMessage()));
       }
       return ResponseEntity.ok().build();
   }

   /**
    * 63. 게시판 타입 삭제하는 API 작성
    * - 삭제하는 게시글이 있는 경우 삭제 안됨
    * */
   @DeleteMapping("/api/board/type/{id}")
   public ResponseEntity<?> deleteBoardType(@PathVariable Long id) {

       ServiceResult result = boardService.deleteBoard(id);

       if (!result.isResult()) {
           return ResponseEntity.ok().body(ResponseMessage.fail(result.getMessage()));
       }
       return ResponseEntity.ok().build();
   }

   /**
    * 64.게시판 타입의 목록을 리턴하는 API 작성
    *
    * */
   @GetMapping("/api/board/type")
   public ResponseEntity<?> boardType() {

       List<BoardType> boardTypeList = boardService.getAllBoardType();
       return ResponseEntity.ok().body(ResponseMessage.success(boardTypeList));
   }


}
















