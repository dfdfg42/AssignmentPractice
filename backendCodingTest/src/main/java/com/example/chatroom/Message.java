package com.example.chatroom;

import java.time.Instant;

public class Message {
    private final long id;
    private final String content;
    private final String sender;
    private final Instant timestamp;

    public Message(long id, String sender, String content) {
        this.id = id;
        this.sender = sender;
        this.content = content;
        this.timestamp = Instant.now();
    }

    public long getId() { return id; }
    public String getSender() { return sender; }
    public String getContent() { return content; }
    public Instant getTimestamp() { return timestamp; }
}
