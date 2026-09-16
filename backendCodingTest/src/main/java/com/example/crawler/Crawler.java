package com.example.crawler;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * 문제 2. 고성능 크롤러
 *
 * 핵심 아이디어:
 *  - 10000개 URL을 순서대로(동기) 처리하면 각 요청 1초만 잡아도 2시간 이상 걸림
 *  - ExecutorService로 스레드풀 생성 → 모든 URL을 동시에 요청 (비동기 병렬)
 *  - Future.get()으로 모든 작업이 끝날 때까지 대기
 *
 * 실행: $ java Crawler < urls.txt
 */
public class Crawler {

    private static final int THREAD_POOL_SIZE = 50; // 동시 요청 수
    private static final int TIMEOUT_SEC = 10;

    public static void main(String[] args) throws Exception {
        // 1. stdin에서 URL 목록 읽기
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        List<String> urls = new ArrayList<>();
        String line;
        while ((line = br.readLine()) != null && !line.isBlank()) {
            urls.add(line.trim());
        }
        System.out.println("총 " + urls.size() + "개 URL 크롤링 시작");

        // 2. 스레드풀 + HttpClient 생성
        ExecutorService executor = Executors.newFixedThreadPool(THREAD_POOL_SIZE);
        HttpClient client = HttpClient.newBuilder()
                .connectTimeout(Duration.ofSeconds(TIMEOUT_SEC))
                .build();

        // 3. 모든 URL에 대해 비동기 작업 제출
        List<Future<?>> futures = new ArrayList<>();
        for (int i = 0; i < urls.size(); i++) {
            final int fileIdx = i + 1;      // 1.html, 2.html, ...
            final String url = urls.get(i);

            futures.add(executor.submit(() -> crawl(client, url, fileIdx)));
        }

        // 4. 모든 작업 완료 대기
        for (Future<?> f : futures) f.get();
        executor.shutdown();
        System.out.println("완료!");
    }

    static void crawl(HttpClient client, String url, int idx) {
        String content;
        try {
            HttpRequest req = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .timeout(Duration.ofSeconds(TIMEOUT_SEC))
                    .GET()
                    .build();

            HttpResponse<String> res = client.send(req, HttpResponse.BodyHandlers.ofString());
            content = (res.statusCode() == 200) ? res.body() : "FAILED";

        } catch (Exception e) {
            content = "FAILED"; // 네트워크 오류, 타임아웃 등
        }

        try {
            Files.writeString(Path.of(idx + ".html"), content);
            System.out.println(idx + ".html 저장");
        } catch (Exception e) {
            System.err.println(idx + ".html 저장 실패: " + e.getMessage());
        }
    }
}
