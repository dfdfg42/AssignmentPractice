package com.example.couponService.Service.exeption;

public class DuplicateCouponIssueException extends RuntimeException {
    public DuplicateCouponIssueException(String message) {
        super(message);
    }
}
