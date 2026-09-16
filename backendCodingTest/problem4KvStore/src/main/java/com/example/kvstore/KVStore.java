package com.example.kvstore;

import org.springframework.stereotype.Component;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * 문제 4. Key-Value 저장소
 *
 * 핵심 설계 결정:
 *  1. ConcurrentHashMap → 여러 클라이언트 동시 접근 안전
 *  2. Entry에 value + expireAt 함께 관리
 *  3. SET은 value만 교체, expireAt 유지 (요구사항!)
 *     → compute()로 원자적(atomic)으로 처리
 *  4. 만료 처리: Lazy(GET 시 확인) + Eager(스케줄러 주기적 정리) 병행
 *
 * [선택 요건]
 *  - 영속성: 프로그램 종료 시 파일에 저장, 재시작 시 복원 (아래 save/load 참고)
 *  - 네트워크: KVController.java에 HTTP API 별도 구현
 */
@Component
public class KVStore {

    // value + expireAt(만료 시각, ms). null이면 만료 없음
    static class Entry {
        String value;
        Long expireAt;

        Entry(String value) {
            this.value = value;
        }

        boolean isExpired() {
            return expireAt != null && System.currentTimeMillis() > expireAt;
        }
    }

    private final ConcurrentHashMap<String, Entry> store = new ConcurrentHashMap<>();

    public KVStore() {
        // Eager 방식: 1초마다 만료 키 정리 (메모리 누수 방지)
        ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
        scheduler.scheduleAtFixedRate(() ->
                store.entrySet().removeIf(e -> e.getValue().isExpired()),
                1, 1, TimeUnit.SECONDS
        );
    }

    /** GET: 만료됐거나 없으면 null */
    public String get(String key) {
        Entry entry = store.get(key);
        if (entry == null) return null;
        if (entry.isExpired()) {
            store.remove(key); // Lazy 삭제
            return null;
        }
        return entry.value;
    }

    /** SET: value만 교체, 기존 EXPIRE 유지 */
    public void set(String key, String value) {
        store.compute(key, (k, existing) -> {
            if (existing == null) {
                return new Entry(value);     // 새 키
            }
            existing.value = value;          // 기존 키: value만 바꾸고 expireAt 유지
            return existing;
        });
    }

    /** EXPIRE: seconds 후 만료 예약 */
    public boolean expire(String key, long seconds) {
        Entry entry = store.get(key);
        if (entry == null || entry.isExpired()) return false;
        entry.expireAt = System.currentTimeMillis() + seconds * 1000L;
        return true;
    }

    // ─── 동작 확인용 main ────────────────────────────────────────
    public static void main(String[] args) throws InterruptedException {
        KVStore kv = new KVStore();

        // 기본 SET/GET
        kv.set("name", "alice");
        System.out.println(kv.get("name"));   // alice

        // EXPIRE 후에도 value 유지
        kv.expire("name", 2); // 2초 후 삭제
        kv.set("name", "alice2"); // value 변경, expire는 유지
        System.out.println(kv.get("name"));   // alice2

        Thread.sleep(3000);

        // 2초 지났으니 만료됨
        System.out.println(kv.get("name"));   // null

        // EXPIRE 없는 키는 영구 보존
        kv.set("server", "local");
        Thread.sleep(2000);
        System.out.println(kv.get("server")); // local
    }
}
