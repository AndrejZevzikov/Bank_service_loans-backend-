package com.final_project.loans.service.permenent;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.io.File;
import java.io.IOException;
import java.util.TimerTask;

@AllArgsConstructor
@EnableScheduling
@Slf4j
@Configuration
public class FileCleaner extends TimerTask {

    public static final String LOAN_RETURN_SCHEDULE_DIRECTORY_ABSOLUTE_PATH =
            "C:\\Users\\andre\\Desktop\\Final_Project\\Backend\\loans\\src\\main\\resources\\schedulePdf";

    @Override
    @Scheduled(cron = "0 0/10 * * * *")
    public void run() {
        log.info("Start cleaning schedule directory");
        File directory = new File(LOAN_RETURN_SCHEDULE_DIRECTORY_ABSOLUTE_PATH);
        try {
            if (directory.list().length > 0) {
                FileUtils.cleanDirectory(directory);
                log.info("Cleaned up schedule directory");
            }
        } catch (IOException e) {
            log.error("Can't delete files");
        }
    }
}
