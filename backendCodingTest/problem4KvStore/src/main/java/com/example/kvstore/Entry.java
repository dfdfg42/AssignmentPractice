package com.example.kvstore;

public class Entry {
    private String value;
    private Long expireAt; // null이면 만료 없음. System.currentTimeMillis() 기준

    public Entry(String value) {
        this.value = value;
        this.expireAt = null;
    }

    public String getValue() { return value; }
    public void setValue(String value) { this.value = value; }

    public Long getExpireAt() { return expireAt; }
    public void setExpireAt(Long expireAt) { this.expireAt = expireAt; }

    public boolean isExpired() {
        return expireAt != null && System.currentTimeMillis() > expireAt;
    }
}
