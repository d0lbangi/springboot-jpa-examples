package com.example.jpa.user.service;

import com.example.jpa.board.model.ServiceResult;
import com.example.jpa.user.entity.User;
import com.example.jpa.user.model.*;

import java.util.List;


public interface UserService {

    UserSummary getUserStatusCount();
    List<User> getTodayUsers();
    List<UserNoticeCount> getUserNoticeCount();
    List<UserLogCount> getUserLogCount();
    /**
     * 좋아요를 가장 많이 한 사용자 목록
     * */
    List<UserLogCount> getUserLikeBest();

    /**
     * 관심 사용자 등록
     * */
    ServiceResult addInterestUser(String email, Long id);

    /**
     * 관심 사용자 삭제
     **/
    ServiceResult removeInterestUser(String email, Long interestId);


    /**
     * 로그인 정보 확인
     * */
    User login(UserLogin userLogin);

    /**
     * 회원 가입
     */
    ServiceResult addUser(UserInput userInput);

    /**
     * 비밀번호 초기화 진행
     */
    ServiceResult resetPassword(UserPasswordResetInput userPasswordResetInput);

    /**
     * 서비스 이용내역 통지
     */
    void sendServiceNotice();
}
