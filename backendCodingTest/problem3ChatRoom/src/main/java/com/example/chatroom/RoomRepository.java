package com.example.chatroom;

import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Repository
public class RoomRepository {

    // roomId → 메시지 목록
    private final Map<String, List<Message>> rooms = new ConcurrentHashMap<>();
    // 메시지 ID 자동 증가
    private final AtomicLong messageIdCounter = new AtomicLong(0);

    // 메시지 저장 (24시간 이내 메시지만 유지)
    public synchronized Message addMessage(String roomId, String sender, String content) {
        rooms.putIfAbsent(roomId, new ArrayList<>());
        Message msg = new Message(messageIdCounter.incrementAndGet(), sender, content);
        rooms.get(roomId).add(msg);
        return msg;
    }

    // 특정 방의 메시지 조회 - afterId 이후 메시지, 24시간 이내만
    public List<Message> getMessages(String roomId, long afterId) {
        List<Message> all = rooms.getOrDefault(roomId, new ArrayList<>());
        Instant cutoff = Instant.now().minusSeconds(24 * 60 * 60); // 24시간 전

        return all.stream()
                .filter(m -> m.getId() > afterId)               // afterId 이후
                .filter(m -> m.getTimestamp().isAfter(cutoff))   // 24시간 이내
                .collect(Collectors.toList());
    }

    // 만료된 메시지 정리 (스케줄러에서 호출)
    public synchronized void removeExpiredMessages() {
        Instant cutoff = Instant.now().minusSeconds(24 * 60 * 60);
        rooms.values().forEach(list ->
                list.removeIf(m -> m.getTimestamp().isBefore(cutoff))
        );
    }
}
