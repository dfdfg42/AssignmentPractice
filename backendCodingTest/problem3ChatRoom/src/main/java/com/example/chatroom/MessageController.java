package com.example.chatroom;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/rooms")
public class MessageController {

    private final RoomRepository roomRepository;

    public MessageController(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    /**
     * 메시지 전송
     * POST /rooms/{roomId}/messages
     * Body: { "sender": "alice", "content": "안녕!" }
     */
    @PostMapping("/{roomId}/messages")
    public ResponseEntity<Message> sendMessage(
            @PathVariable String roomId,
            @RequestBody Map<String, String> body) {
        String sender = body.get("sender");
        String content = body.get("content");
        Message msg = roomRepository.addMessage(roomId, sender, content);
        return ResponseEntity.ok(msg);
    }

    /**
     * 메시지 조회
     * GET /rooms/{roomId}/messages?after=0
     * - after=0 이면 처음 입장 → 저장된 모든 메시지 반환
     * - after={lastMessageId} 이면 해당 ID 이후 메시지만 반환
     */
    @GetMapping("/{roomId}/messages")
    public ResponseEntity<List<Message>> getMessages(
            @PathVariable String roomId,
            @RequestParam(defaultValue = "0") long after) {
        List<Message> messages = roomRepository.getMessages(roomId, after);
        return ResponseEntity.ok(messages);
    }
}
