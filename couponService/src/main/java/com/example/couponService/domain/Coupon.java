package com.example.couponService.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;

import java.time.LocalDateTime;

@Entity
@Getter
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

    public void incrementIssuedQuantity() {
        if (issuedQuantity == null) {
            issuedQuantity = 1;
        } else {
            issuedQuantity = issuedQuantity + 1;
        }
    }

    public int getIssuedQuantityOrZero() {
        return issuedQuantity == null ? 0 : issuedQuantity;
    }

    public boolean isActive() {
        return status == Status.ACTIVE;
    }

    public boolean isWithinIssuePeriod(LocalDateTime now) {
        if (startDate != null && now.isBefore(startDate)) {
            return false;
        }
        if (endDate != null && now.isAfter(endDate)) {
            return false;
        }
        return true;
    }

    public boolean isSoldOut() {
        if (totalQuantity == null) {
            return false;
        }
        return getIssuedQuantityOrZero() >= totalQuantity;
    }
}
