package com.example.jpa.common;

import com.example.jpa.logs.repository.LogService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class Scheduler {

    private final LogService logService;

    /**
     * 99. 스프링 스케줄러를 이용하여 매일 새벽 4시에 로그정보를 삭제하는 기능을 작성
     */
//    @Scheduled(cron = "0 0 4 * * *")
    @Scheduled(fixedDelay = 1000 * 60)
    public void deleteLog() {
        log.info("##################### -1");
        log.info("##################### -1");
        log.info("##################### -1");
        log.info("##################### -1");
        log.info("스케줄 실행!!!!!!!!!!!!!!!");

        logService.deleteLog();
     }
}
