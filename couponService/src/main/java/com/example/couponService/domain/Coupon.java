package com.example.couponService.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import java.time.LocalDateTime;

@Entity
public class Coupon {

    @Id
    Long id;

    String name;

    String description;

    CouponType couponType;

    Status status;

    Integer discountValue;

    //최소 주문 금액
    Integer minOrderAmount;

    //최대 할인 금액
    Integer maxDiscountAmount;

    //총 발급 수량
    Integer totalQuantity;

    //현재 발급 수량
    Integer issuedQuantity;

    //발급 후 유효 일수
    Integer validDays;

    //발급 시작일
    LocalDateTime startDate;

    //발급 종료일
    LocalDateTime endDate;

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public CouponType getCouponType() {
        return couponType;
    }

    public Status getStatus() {
        return status;
    }

    public Integer getDiscountValue() {
        return discountValue;
    }

    public Integer getMinOrderAmount() {
        return minOrderAmount;
    }

    public Integer getMaxDiscountAmount() {
        return maxDiscountAmount;
    }

    public Integer getTotalQuantity() {
        return totalQuantity;
    }

    public Integer getIssuedQuantity() {
        return issuedQuantity;
    }

    public Integer getValidDays() {
        return validDays;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }
}
