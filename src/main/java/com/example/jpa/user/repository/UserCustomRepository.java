package com.example.jpa.user.repository;

import com.example.jpa.user.entity.User;
import com.example.jpa.user.model.UserLogCount;
import com.example.jpa.user.model.UserNoticeCount;
import com.example.jpa.user.model.UserStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Repository
public class UserCustomRepository {

    private final EntityManager entityManager;

    public List<UserNoticeCount> findUserNoticeCount() {

        String sql = " select u.id, u.email, u.user_name, (select count(*) from Notice n where n.user_id = u.id) notice_count from User u";

        List<UserNoticeCount> List = entityManager.createNativeQuery(sql).getResultList();
        return List;
    }

    public List<UserLogCount> findUserLogCOunt() {

        String sql = " select u.id, u.email, u.user_name " +
                ", (select count(*) from notice n where n.user_id = u.id) notice_count " +
                ", (select count(*) from notice_like nl where nl.user_id = u.id) notice_like_count " +
                " from user u";
        List<UserLogCount> List = entityManager.createNativeQuery(sql).getResultList();
        return List;
    }
}


















