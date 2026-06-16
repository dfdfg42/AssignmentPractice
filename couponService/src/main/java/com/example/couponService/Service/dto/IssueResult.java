package com.example.couponService.Service.dto;

import com.example.couponService.domain.IssuedCoupon;
import java.time.LocalDateTime;

public class IssueResult {
    private Long issuedCouponId;
    private Long couponId;
    private String couponName;
    private String status;
    private LocalDateTime issuedAt;
    private LocalDateTime expiredAt;

    public IssueResult(Long issuedCouponId, Long couponId, String couponName,
                       String status, LocalDateTime issuedAt, LocalDateTime expiredAt) {
        this.issuedCouponId = issuedCouponId;
        this.couponId = couponId;
        this.couponName = couponName;
        this.status = status;
        this.issuedAt = issuedAt;
        this.expiredAt = expiredAt;
    }

    public IssueResult(IssuedCoupon issuedCoupon) {
        this(issuedCoupon.getId(),
             issuedCoupon.getCoupon().getId(),
             issuedCoupon.getCoupon().getName(),
             issuedCoupon.getStatus().name(),
             issuedCoupon.getIssuedAt(),
             issuedCoupon.getExpiredAt());
    }

    public Long getIssuedCouponId() {
        return issuedCouponId;
    }

    public Long getCouponId() {
        return couponId;
    }

    public String getCouponName() {
        return couponName;
    }

    public String getStatus() {
        return status;
    }

    public LocalDateTime getIssuedAt() {
        return issuedAt;
    }

    public LocalDateTime getExpiredAt() {
        return expiredAt;
    }
}
