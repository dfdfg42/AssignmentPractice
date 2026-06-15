package com.example.couponService.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;

import java.time.LocalDateTime;

@Entity
public class IssuedCoupon {

    @Id
    Long id;

    @OneToOne
    Coupon coupon;

    Long userID;

    Status status;

    LocalDateTime issuedAt;

    LocalDateTime expiredAt;

    LocalDateTime usedAt;
}
