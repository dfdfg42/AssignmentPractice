package com.example.chatroom;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

/**
 * 채팅방 테스트 클라이언트
 * 서버 실행 후 main()을 별도로 실행하세요.
 * Run Configuration에서 "Allow parallel runs" 체크 필요
 */
public class TestClient {

    private static final String BASE = "http://localhost:8080";
    private static final HttpClient client = HttpClient.newHttpClient();

    public static void main(String[] args) throws Exception {
        String roomId = "room1";

        // 1. 메시지 전송
        sendMessage(roomId, "alice", "안녕하세요!");
        sendMessage(roomId, "bob", "반갑습니다!");

        // 2. 처음 입장 - 모든 메시지 조회 (after=0)
        System.out.println("\n=== 처음 입장 (모든 메시지) ===");
        String allMessages = getMessages(roomId, 0);
        System.out.println(allMessages);

        // 3. 메시지 추가 전송
        sendMessage(roomId, "alice", "새 메시지입니다");

        // 4. 마지막 읽은 ID 이후만 조회 (after=2)
        System.out.println("\n=== 이후 메시지만 (after=2) ===");
        String newMessages = getMessages(roomId, 2);
        System.out.println(newMessages);
    }

    static void sendMessage(String roomId, String sender, String content) throws Exception {
        String body = String.format("{\"sender\":\"%s\",\"content\":\"%s\"}", sender, content);
        HttpRequest req = HttpRequest.newBuilder()
                .uri(URI.create(BASE + "/rooms/" + roomId + "/messages"))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(body))
                .build();
        HttpResponse<String> res = client.send(req, HttpResponse.BodyHandlers.ofString());
        System.out.println("[전송] " + sender + ": " + content + " → " + res.statusCode());
    }

    static String getMessages(String roomId, long after) throws Exception {
        HttpRequest req = HttpRequest.newBuilder()
                .uri(URI.create(BASE + "/rooms/" + roomId + "/messages?after=" + after))
                .GET()
                .build();
        HttpResponse<String> res = client.send(req, HttpResponse.BodyHandlers.ofString());
        return res.body();
    }
}
