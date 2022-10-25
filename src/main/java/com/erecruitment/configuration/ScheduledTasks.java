package com.erecruitment.configuration;


import com.erecruitment.services.PengajuanSDMService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

@Component
@EnableScheduling
public class ScheduledTasks {

    private static final Logger log = LoggerFactory.getLogger(ScheduledTasks.class);
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
    @Autowired
    private PengajuanSDMService pengajuanSDMService;

    @Scheduled(cron = "${cron.scheduledtime2}")
    public void reportCurrentTime() {
        log.info("The time is now {}", dateFormat.format(new Date()));
        pengajuanSDMService.closeAutoJobPosted();
    }

}
