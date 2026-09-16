package com.example.couponService.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;

import java.time.LocalDateTime;

@Entity
public class CouponUsageHistory {

    @Id
    Long id;

    @OneToOne
    IssuedCoupon issuedCoupon;

    Long userId;

    Long orderId;

    Integer discountAmount;

    LocalDateTime usedAt;

}
