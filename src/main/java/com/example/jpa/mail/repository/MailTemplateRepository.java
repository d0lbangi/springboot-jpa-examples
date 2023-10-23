package com.example.jpa.mail.repository;

import com.example.jpa.mail.entity.MailTemplate;
import com.example.jpa.user.entity.User;
import com.example.jpa.user.model.UserStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface MailTemplateRepository extends JpaRepository<MailTemplate, Long> {


    Optional<MailTemplate> findByTemplateId(String templateId);
}
