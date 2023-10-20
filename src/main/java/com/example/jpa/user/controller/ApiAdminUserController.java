package com.example.jpa.user.controller;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.example.jpa.notice.entity.Notice;
import com.example.jpa.notice.entity.NoticeLike;
import com.example.jpa.notice.model.NoticeResponse;
import com.example.jpa.notice.model.ResponseError;
import com.example.jpa.notice.repository.NoticeLikeRepository;
import com.example.jpa.notice.repository.NoticeRepository;
import com.example.jpa.user.entity.User;
import com.example.jpa.user.entity.UserLoginHistory;
import com.example.jpa.user.exception.ExistsEmailException;
import com.example.jpa.user.exception.PasswordNotMatchException;
import com.example.jpa.user.exception.UserNotFoundException;
import com.example.jpa.user.model.*;
import com.example.jpa.user.repository.UserLoginHistoryRepository;
import com.example.jpa.user.repository.UserRepository;
import com.example.jpa.util.JWTUtils;
import com.example.jpa.util.PasswordUtils;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.*;

@RequiredArgsConstructor
@RestController
public class ApiAdminUserController {

    private final UserRepository userRepository;
    private final NoticeRepository noticeRepository;
    private final UserLoginHistoryRepository userLoginHistoryRepository;

    /**
     * 48. 사용자 목록과 사용자 수를 함께 내리는 REST API 작성
     * ResponseData의 구조를 아래와 같이 형식으로 작성해서 결과 리턴
     * {
     *   "totalCount": N
     *   "data": user목록 컬렉션
     * }
     *
     * */
    /*@GetMapping("/api/admin/user")
    public ResponseMessage userList() {

        List<User> userList = userRepository.findAll();
        long totalUserCount = userRepository.count();

        return ResponseMessage.builder()
                .totalUserCount(totalUserCount)
                .data(userList)
                .build();

    }*/


    /**
     * 49. 사용자 상세 조회를 조회하는 API 를 아래 조건에 맞게 구현
     * ResponseMessage 클래스로 추상화해서 전송
     * {
     *     "header": {
     *         result: true|false
     *         , resultCode: string
     *         , message: error message or alert message
     *         , status: http result code
     *     },
     *     "body" : 내려야 할 데이터가 있는 경우 body 를 통해서 전송
     * }
     * */

    @GetMapping("/api/admin/user/{id}")
    public ResponseEntity<?> userDetail(@PathVariable Long id) {

        Optional<User> user = userRepository.findById(id);
        if (!user.isPresent()) {
            return new ResponseEntity<>(ResponseMessage.fail("사용자 정보가 존재하지 않습니다"), HttpStatus.BAD_REQUEST);
        }
            return ResponseEntity.ok().body(ResponseMessage.success(user));
    }

    /**
     * 50. 사용자 목록 조회에 대한 검색을 리턴하는 API 작성
     * - 이메일, 이름, 전화번호에 대한 검색결과를 리턴(각 항목은 or 조건)
     * */

    @GetMapping("/api/admin/user/search")
    public ResponseEntity<?> findUser(@RequestBody UserSearch userSearch) {

        List<User> userList =
                userRepository.findByEmailContainsOrPhoneContainsOrUserNameContains(userSearch.getEmail()
                , userSearch.getPhone()
                , userSearch.getUserName());

        return ResponseEntity.ok().body(ResponseMessage.success(userList));
    }

    /**
     * 51. 사용자의 상태를 변경하는 API 작성
     * - 사용자 상태: (정상, 정지)
     * 0 이에 대한 플래그값은 사용자상태(정상: Using, 정지: Stop)
     * */
    @PatchMapping("/api/admin/user/{id}/status")
    public ResponseEntity<?> userStatus(@PathVariable Long id, @RequestBody UserStatusInput userStatusInput) {

        Optional<User> optionalUser = userRepository.findById(id);

        if(!optionalUser.isPresent()) {
            return new ResponseEntity<>(ResponseMessage.fail("사용자 정보가 없습니다."), HttpStatus.BAD_REQUEST);
        }


        User user = optionalUser.get();

        user.setStatus(userStatusInput.getStatus());
        userRepository.save(user);

        return ResponseEntity.ok().build();
    }

    /**
     * 52. 사용자 정보를 삭제하는 API 작성
     * - 작성된 게시글이 있으면 예외 발생 처리
     * */
    @DeleteMapping("/api/admin/user/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {

        Optional<User> optionalUser = userRepository.findById(id);
        if(!optionalUser.isPresent()) {
            return new ResponseEntity<>(ResponseMessage.fail("사용자 정보가 존재하지 않습니다."), HttpStatus.BAD_REQUEST);
        }

        User user = optionalUser.get();

        if (noticeRepository.countByUser(user) > 0) {
            return new ResponseEntity<>(ResponseMessage.fail("사용자가 작성한 공지사항이 있습니다."), HttpStatus.BAD_REQUEST);
        }
        userRepository.delete(user);

        return ResponseEntity.ok().build();
    }

    /**
     * 53. 사용자가 로그인을 했을 때 이에 대한 접속 이력이 저장된다고 했을 때, 이에 대한 접속 이력을 조회하는 API
     * - 접속이력 정보가 있다는 가정하에 API 작성
     * - UserLoginHistory 엔티티를 통해서 구현
     * */
    @GetMapping("/api/admin/user/login/history")
    public ResponseEntity<?> userLoginHistory(){

        List<UserLoginHistory> userLoginHistories = userLoginHistoryRepository.findAll();
        return ResponseEntity.ok().body(userLoginHistories);
    }

    /**
     * 54. 사용자의 접속을 제한하는 API 구현
     * */
    @PatchMapping("/api/admin/user/{id}/lock")
    public ResponseEntity<?> userLock(@PathVariable Long id) {

        Optional<User> optionalUser = userRepository.findById(id);
        if(!optionalUser.isPresent()) {
            return new ResponseEntity<>(ResponseMessage.fail("사용자 정보가 없습니다."), HttpStatus.BAD_REQUEST);
        }

        User user = optionalUser.get();
        if (user.isLockYn()) {
            return new ResponseEntity<>(ResponseMessage.fail("이미 접속 제한이 된 사용자 입니다."), HttpStatus.BAD_REQUEST);
        }

        user.setLockYn(true);
        userRepository.save(user);

        return ResponseEntity.ok().body(ResponseMessage.success());
    }
}
















