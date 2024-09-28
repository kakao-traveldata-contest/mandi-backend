package com.tourapi.mandi.domain.course.route;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Profile("dev")
@Component
@RequiredArgsConstructor
public class CourseRouteCollector {

    private final CourseRouteApiClient apiClient;

    /**
     * 주중 오전 8시에 API 요청을 수행합니다.
     **/
    @Scheduled(cron = "0 0 8 ? * MON-FRI")
    public void collect() {
        log.info("--- starting collect gpx task.. ---");
        apiClient.requestRoutes();
        log.info("--- finished task :) ---");
    }
}
