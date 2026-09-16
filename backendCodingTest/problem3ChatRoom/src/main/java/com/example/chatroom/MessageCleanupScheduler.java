package com.example.chatroom;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class MessageCleanupScheduler {

    private final RoomRepository roomRepository;

    public MessageCleanupScheduler(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    // 1분마다 24시간 지난 메시지 제거
    @Scheduled(fixedDelay = 60_000)
    public void cleanup() {
        roomRepository.removeExpiredMessages();
    }
}
