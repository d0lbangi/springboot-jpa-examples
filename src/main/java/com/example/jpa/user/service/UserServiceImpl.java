package com.example.jpa.user.service;

import com.example.jpa.board.model.ServiceResult;
import com.example.jpa.common.MailComponent;
import com.example.jpa.common.exception.BizException;
import com.example.jpa.common.model.ResponseResult;
import com.example.jpa.logs.repository.LogService;
import com.example.jpa.mail.entity.MailTemplate;
import com.example.jpa.mail.repository.MailTemplateRepository;
import com.example.jpa.user.entity.User;
import com.example.jpa.user.entity.UserInterest;
import com.example.jpa.user.model.*;
import com.example.jpa.user.repository.UserCustomRepository;
import com.example.jpa.user.repository.UserInterestRepository;
import com.example.jpa.user.repository.UserRepository;
import com.example.jpa.util.PasswordUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserCustomRepository userCustomRepository;
    private final UserInterestRepository userInterestRepository;

    private final MailComponent mailComponent;
    private final MailTemplateRepository mailTemplateRepository;

    @Override
    public UserSummary getUserStatusCount() {

        long usingUserCount = userRepository.countByStatus(UserStatus.Using);
        long stopUserCount = userRepository.countByStatus(UserStatus.Stop);
        long totalUserCount = userRepository.count();

        return UserSummary.builder()
                .usingUserCount(usingUserCount)
                .stopUserCount(stopUserCount)
                .totalUserCount(totalUserCount)
                .build();
    }

    @Override
    public List<User> getTodayUsers() {

        LocalDateTime t = LocalDateTime.now();
        LocalDateTime startDate = LocalDateTime.of(t.getYear(), t.getMonth(), t.getDayOfMonth(), 0, 0);
        LocalDateTime endDate = startDate.plusDays(1);

        return userRepository.findToday(startDate, endDate);
    }

    @Override
    public List<UserNoticeCount> getUserNoticeCount() {

        return userCustomRepository.findUserNoticeCount();

    }

    @Override
    public List<UserLogCount> getUserLogCount() {

        return userCustomRepository.findUserLogCount();
    }

    @Override
    public List<UserLogCount> getUserLikeBest() {

        return userCustomRepository.findUserLikeBest();
    }

    @Override
    public ServiceResult addInterestUser(String email, Long id) {

        Optional<User> optionalUser = userRepository.findByEmail(email);
        if (!optionalUser.isPresent()) {
            return ServiceResult.fail("회원 정보가 존재하지 않습니다.");
        }
        User user = optionalUser.get();

        Optional<User> optionalInterestUser = userRepository.findById(id);
        if (!optionalInterestUser.isPresent()) {
            return ServiceResult.fail("관심사용자에 추가할 회원 정보가 존재하지 않습니다.");
        }
        User interestUser = optionalInterestUser.get();

        //내가 나를 추가해?
        if (user.getId() == interestUser.getId()) {
            return ServiceResult.fail(" 자기자신은 추가할 수 없습니다. ");
        }

        if (userInterestRepository.countByUserAndInterestUser(user, interestUser) > 0) {
            return ServiceResult.fail("이미 관심사용자 목록에 추가하였습니다.");
        }

        UserInterest userInterest = UserInterest.builder()
                .user(user)
                .interestUser(interestUser)
                .regDate(LocalDateTime.now())
                .build();
        userInterestRepository.save(userInterest);

        return ServiceResult.success();
    }

    @Override
    public ServiceResult removeInterestUser(String email, Long interestId) {

        Optional<User> optionalUser = userRepository.findByEmail(email);
        if (!optionalUser.isPresent()) {
            return ServiceResult.fail("회원 정보가 존재하지 않습니다.");
        }
        User user = optionalUser.get();

        Optional<UserInterest> optionalUserInterest = userInterestRepository.findById(interestId);
        if (!optionalUserInterest.isPresent()) {
            return ServiceResult.fail("삭제할 정보가 없습니다.");
        }
        UserInterest userInterest = optionalUserInterest.get();

        if(userInterest.getUser().getId() != user.getId()){
            return ServiceResult.fail("본인의 관심자 정보만 삭제할 수 있습니다.");
        }

        userInterestRepository.delete(userInterest);
        return ServiceResult.success();

    }

    @Override
    public User login(UserLogin userLogin) {

        Optional<User> optionalUser = userRepository.findByEmail(userLogin.getEmail());
        if (!optionalUser.isPresent()) {
            throw new BizException("회원 정보가 존재하지 않습니다.");
        }
        User user = optionalUser.get();

        if (!PasswordUtils.equalPassword(userLogin.getPassword(), user.getPassword())) {
            throw new BizException("일치하는 정보가 없습니다.");
        }

        return user;

    }

    @Override
    public ServiceResult addUser(UserInput userInput) {

        Optional<User> optionalUser = userRepository.findByEmail(userInput.getEmail());
        if (optionalUser.isPresent()) {
            throw new BizException("이미 가입된이메일이 있습니다.");
        }

        String encryptPassword = PasswordUtils.encryptedPassword(userInput.getPassword());

        User user = User.builder()
                .email(userInput.getEmail())
                .userName(userInput.getUserName())
                .regDate(LocalDateTime.now())
                .password("")
                .phone(userInput.getPhone())
                .status(UserStatus.Using)
                .build();

        userRepository.save(user);

        // 메일을 전송
        String fromEmail = "d0d0lbangi13@gmail.com"; // 보내는 사람
        String fromName = "관리자";
        String toEmail = user.getEmail();
        String toName = user.getUserName();

        String title = "회원가입을 축하합니다.";
        String contents = "회원가입을 축하합니다.";


        mailComponent.send(fromEmail, fromName, toEmail, toName, title, contents);
        return ServiceResult.success();
    }

    @Override
    public ServiceResult resetPassword(UserPasswordResetInput userInput) {

        Optional<User> optionalUser = userRepository.findByEmailAndUserName(userInput.getEmail(), userInput.getUserName());
        if (optionalUser.isPresent()) {
            throw new BizException("회원 정보가 존재하지 않습니다.");
        }
        User user = optionalUser.get();

        String passwordResetKey = UUID.randomUUID().toString();
        user.setPasswordResetYn(true);
        user.setPasswordResetKey(passwordResetKey);
        userRepository.save(user);

        String serverUrl = "http://localhost:8080";

        Optional<MailTemplate> optionalMailTemplate = mailTemplateRepository.findByTemplateId("USER_RESET_PASSWORD");
        optionalMailTemplate.ifPresent(e -> {

            String FromEmail = e.getSendEmail();
            String FromUserName = e.getSendUserName();
            String title = e.getTitle().replaceAll("\\{USER_NAME\\}", user.getUserName());
            String contents = e.getContents().replaceAll("\\{USER_NAME\\}", user.getUserName())
                    .replaceAll("\\{SERVER_URL\\}", serverUrl)
                    .replaceAll("\\{RESET_PASSWORD_KEY\\}", passwordResetKey);

            mailComponent.send(FromEmail, FromUserName
                    , user.getEmail(), user.getUserName(), title, contents);
        });
        return ServiceResult.success();
    }


}
