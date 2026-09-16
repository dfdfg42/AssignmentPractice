package com.example.couponService.controller;

import ch.qos.logback.core.model.Model;
import com.example.couponService.Service.CouponIssueQueueService;
import com.example.couponService.domain.Coupon;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@RestController
@RequiredArgsConstructor
public class CouponIssueController {

    private final CouponIssueQueueService couponIssueQueueService;


    @GetMapping("/api/coupons/available")
    public List<AvailableCouponDTO>() {



    }

    @PostMapping("/api/coupons/{couponId}/issue")
    public CompletableFuture<ResponseEntity<?>> issueCoupon(@PathVariable Long couponId, @RequestBody IssueCouponRequest request) {

        return couponIssueQueueService.enqueueIssue(couponId, request.userId)
                .thenApply(result -> ResponseEntity.ok(result));


    }

    private class AvailableCouponDTO {

        Long couponId;
        String name;
        String couponType;
        Integer discountValue;
        Integer totalQuantity;
        Integer remainingQUantity;
        LocalDateTime startDate;
        LocalDateTime endDate;

    }

    private class IssueCouponRequest {
        Long userId;
    }
}
