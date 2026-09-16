package com.example.kvstore;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/kv")
public class KVController {

    private final KVStore kvStore;

    public KVController(KVStore kvStore) {
        this.kvStore = kvStore;
    }

    /**
     * GET /kv/{key}
     * key에 저장된 value 반환. 없거나 만료됐으면 404
     */
    @GetMapping("/{key}")
    public ResponseEntity<Map<String, String>> get(@PathVariable String key) {
        String value = kvStore.get(key);
        if (value == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(Map.of("key", key, "value", value));
    }

    /**
     * POST /kv/{key}
     * Body: { "value": "hello" }
     * key에 value 저장 (기존 EXPIRE는 유지)
     */
    @PostMapping("/{key}")
    public ResponseEntity<String> set(
            @PathVariable String key,
            @RequestBody Map<String, String> body) {
        kvStore.set(key, body.get("value"));
        return ResponseEntity.ok("OK");
    }

    /**
     * POST /kv/{key}/expire
     * Body: { "seconds": 60 }
     * key를 seconds 후에 삭제 예약
     */
    @PostMapping("/{key}/expire")
    public ResponseEntity<String> expire(
            @PathVariable String key,
            @RequestBody Map<String, Long> body) {
        boolean result = kvStore.expire(key, body.get("seconds"));
        if (!result) return ResponseEntity.notFound().build();
        return ResponseEntity.ok("OK");
    }
}
