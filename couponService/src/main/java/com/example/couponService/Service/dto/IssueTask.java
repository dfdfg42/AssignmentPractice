package com.example.couponService.Service.dto;

import java.util.concurrent.CompletableFuture;

public class IssueTask {

    private final Long couponId;
    private final Long userId;
    private final CompletableFuture<IssueResult> future;

    public IssueTask(Long couponId, Long userId, CompletableFuture<IssueResult> future) {
        this.couponId = couponId;
        this.userId = userId;
        this.future = future;
    }

    public Long getCouponId() {
        return couponId;
    }

    public Long getUserId() {
        return userId;
    }

    public CompletableFuture<IssueResult> getFuture() {
        return future;
    }
}
