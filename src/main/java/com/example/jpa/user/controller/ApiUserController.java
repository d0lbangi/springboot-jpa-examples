package com.example.jpa.user.controller;

import com.example.jpa.notice.entity.Notice;
import com.example.jpa.notice.model.ResponseError;
import com.example.jpa.notice.repository.NoticeRepository;
import com.example.jpa.user.entity.User;
import com.example.jpa.user.exception.ExistsEmailException;
import com.example.jpa.user.exception.UserNotFoundException;
import com.example.jpa.user.model.UserInput;
import com.example.jpa.user.model.UserResponse;
import com.example.jpa.user.model.UserUpdate;
import com.example.jpa.notice.model.NoticeResponse;
import com.example.jpa.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.ErrorState;
import org.apache.coyote.Response;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@RestController
public class ApiUserController {

    private final UserRepository userRepository;
    private final NoticeRepository noticeRepository;


    /* 31. 사용자 등록시 입력값 유효하지 않은 경우 예외를 발생시키는 기능 작성
    입력값: 이메일(ID), 이름, 비밀번호, 연락처
    사용자 정의 에러 모델을 이용하여 에러를 리턴*/
/*    @PostMapping("/api/user")
    public ResponseEntity<?> addUser(@RequestBody @Valid UserInput userInput, Errors errors) {

        List<ResponseError> responseErrorList = new ArrayList<>();

        if (errors.hasErrors()) {
            errors.getAllErrors().forEach((e) -> {
                responseErrorList.add(ResponseError.of((FieldError)e));
            });
            return new ResponseEntity<>(responseErrorList, HttpStatus.BAD_REQUEST);
        }
        return ResponseEntity.ok().build();
        // return new ResponseEntity<>(HttpStatus.OK);
    }*/



    /* 32. 사용자 정보르 입력받아서 저장하는 API 를 작성해 보세요.
    입력값: 이메일(유일한 값 확인), 이름, 비밀번호, 연락처, 가입일(현재일시)*/
/*    @PostMapping("/api/user")
    public ResponseEntity<?> addUser(@RequestBody @Valid UserInput userInput, Errors errors) {

        List<ResponseError> responseErrorList = new ArrayList<>();
        if (errors.hasErrors()) {
            errors.getAllErrors().forEach((e) -> {
                responseErrorList.add(ResponseError.of((FieldError) e));
            });
            return new ResponseEntity<>(responseErrorList, HttpStatus.BAD_REQUEST);
        }

        User user = User.builder()
                .email(userInput.getEmail())
                .userName(userInput.getUserName())
                .password(userInput.getPassword())
                .phone(userInput.getPhone())
                .regDate(LocalDateTime.now())
                .build();
        userRepository.save(user);
        return ResponseEntity.ok().build();
    }*/


    /**
     * 33. 사용자 수정을 수정하는 API를 다음 조건에 맞게 작성해 보세요.
     * 사용자 정보가 없는 경우 UserNotFoundException 발생
     * 에러 메시지는 "사용자 정보가 없습니다."
     * 수정정보는 연락처만 수정가능, 수정일자는 현재 날짜
     * */
    @PutMapping("/api/user/{id}")
    public HttpEntity<?> updateUser(@PathVariable Long id, @RequestBody @Valid UserUpdate userUpdate, Errors errors) {

        List<ResponseError> responseErrorList = new ArrayList<>();

        if(errors.hasErrors()) {
            errors.getAllErrors().forEach((e) -> {
                responseErrorList.add(ResponseError.of((FieldError)e));
            });
            return new ResponseEntity<>(responseErrorList, HttpStatus.BAD_REQUEST);
        }

        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("사용자 정보가 없습니다."));

        user.setPhone(userUpdate.getPhone());
        user.setUpdateDate(LocalDateTime.now());
        userRepository.save(user);

        return ResponseEntity.ok().build();
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<?> UserNotFoundExceptionHandler(UserNotFoundException exception) {
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
    }


    /**
    * 34. 사용자 정보 조회(가입한 아이디에 대한)의 기능을 수행하는 API를 작성해 보세요.
    * 다만, 보안상 비밀번호와 가입일, 회원정보 수정일은 내리지 않는다.
    * */
    @GetMapping("/api/user/{id}")
    public UserResponse getuser(@PathVariable Long id) {

        User user = userRepository.findById(id)
                .orElseThrow(()-> new UserNotFoundException("사용자 정보가 없습니다."));

        // UserResponse userResponse = new UserResponse(user);
        UserResponse userResponse = UserResponse.of(user);
        return userResponse;
    }


    /**
     * 35. 내가 작성한 공지사항 목록에 대한 API 작성
     * 삭제일과 삭제자 아이디는 보안상 내리지 않음
     * 작성장 정보를 모두 내리지 않고, 작성자의 아이디와 이름만 내림
     * */
    @GetMapping("/api/user/{id}/notice")
    public List<NoticeResponse> userNotice(@PathVariable Long id) {

        User user = userRepository.findById(id)
                .orElseThrow(()-> new UserNotFoundException("사용자 정보가 없습니다."));

        List<Notice> noticeList = noticeRepository.findByUser(user);

        List<NoticeResponse> noticeResponsesList = new ArrayList<>();
        noticeList.stream().forEach((e)-> {
            noticeResponsesList.add(NoticeResponse.of(e));
        });

        return noticeResponsesList;
    }

    /**
     * 36. 사용자 등록시 이미 존재하는 이메일(이메일은 유일)인 경우 예외를 발생시키는 API를 작성
     * 동일한 이메일에 가입된 회원정보가 존재하는 경우 ExistsEmailException 발생
     * */
    @PostMapping("/api/user")
    public ResponseEntity<?> addUser(@RequestBody UserInput userInput, Errors errors) {

        List<ResponseError> responseErrorList = new ArrayList<>();
        if (errors.hasErrors()) {
            errors.getAllErrors().stream().forEach((e)-> {
                responseErrorList.add(ResponseError.of((FieldError)e ));
            });
            return new ResponseEntity<>(responseErrorList, HttpStatus.BAD_REQUEST);
        }

        if (userRepository.countByEmail(userInput.getEmail()) >0) {
            throw new ExistsEmailException("이미 가입된 이메일 입니다");
        }

        User user = User.builder()
                .email(userInput.getEmail())
                .userName(userInput.getUserName())
                .phone(userInput.getPhone())
                .password(userInput.getPassword())
                .regDate(LocalDateTime.now())
                .build();
        userRepository.save(user);

        return ResponseEntity.ok().build();
    }

    @ExceptionHandler(ExistsEmailException.class)
    public ResponseEntity<?> ExistsEmailExceptionHandler(ExistsEmailException exception) {
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
    }
}

