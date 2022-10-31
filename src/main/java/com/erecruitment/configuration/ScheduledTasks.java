package com.erecruitment.configuration;


import com.erecruitment.dtos.requests.WebSocketDTO;
import com.erecruitment.services.PengajuanSDMService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectedEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import java.text.SimpleDateFormat;
import java.util.Date;

@Component
@EnableScheduling
public class ScheduledTasks {

    private static final Logger log = LoggerFactory.getLogger(ScheduledTasks.class);
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
    @Autowired
    private PengajuanSDMService pengajuanSDMService;
    @Autowired
    private SimpMessageSendingOperations messagingTemplate;

    @Scheduled(cron = "${cron.every5MinuteOn00Until01}")
    public void reportCurrentTime() {
        log.info("Sceduler last run on => ", dateFormat.format(new Date()));
        pengajuanSDMService.closeAutoJobPosted();
    }

    @EventListener
    public void handleWebSocketConnectListener(SessionConnectedEvent event) {
        log.info("Menerima koneksi web socket baru " + event.toString());
    }

    @EventListener
    public void handleWebSocketDisconnectListener(SessionDisconnectEvent event) {
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(event.getMessage());
        String username = (String) accessor.getSessionAttributes().get("username");
        if (username != null) {
            log.info("User disconnected : " + username);
            WebSocketDTO chatMessage = new WebSocketDTO();
            chatMessage.setSender(username);
            chatMessage.setType("LEAVE");
            messagingTemplate.convertAndSend("/topic/applyJob", chatMessage);
        }
    }

}
