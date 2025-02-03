package com.xander.demo.scheduler;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.scheduling.annotation.Scheduled;

import com.xander.demo.config.AppCache;
import com.xander.demo.entity.DemoEntity;
import com.xander.demo.entity.UserEntity;
import com.xander.demo.repository.UserRepositoryImpl;
import com.xander.demo.service.EmailService;
import com.xander.demo.service.SentimentalService;

public class UserScheduler {

    private final EmailService emailService;
    private final UserRepositoryImpl userRepository;
    private final SentimentalService sentimentalService;
    private final AppCache appCache;

    public UserScheduler(EmailService emailService, UserRepositoryImpl userRepository,
            SentimentalService sentimentalService, AppCache appCache) {
        this.emailService = emailService;
        this.userRepository = userRepository;
        this.sentimentalService = sentimentalService;
        this.appCache = appCache;
        }

        @Scheduled(cron = "0 0 9 ? * SUN")
        public void fetchUsersSendSentiMail() {
        List<UserEntity> users = userRepository.getUserForSA();
        for (UserEntity user : users) {
            List<DemoEntity> demoEntities = user.getDemoEntries();
            List<String> filteredEntries = demoEntities.stream()
                    .filter(x -> x.getCreatedDate().isAfter(LocalDateTime.now().minus(7, ChronoUnit.DAYS)))
                    .map(x -> x.getDescription())
                    .collect(Collectors.toList());
            String entry = String.join(" ", filteredEntries);
            String sentiment = sentimentalService.getSentimentalScore(entry);
            emailService.sendEmail(user.getEmail(), "Sentiment for last 7 days", sentiment);
        }
    }

    @Scheduled(cron = "0 0/10 * * * *")
    public void clearAppCache() {
        appCache.init();
    }
}
