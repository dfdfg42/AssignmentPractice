package com.example.couponService.Service.exeption;

public class CouponExhaustedException extends RuntimeException {
    public CouponExhaustedException(String message) {
        super(message);
    }
}
