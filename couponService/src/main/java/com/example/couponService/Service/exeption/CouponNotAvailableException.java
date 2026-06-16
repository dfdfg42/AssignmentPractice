package com.example.couponService.Service.exeption;

public class CouponNotAvailableException extends RuntimeException {
    public CouponNotAvailableException(String message) {
        super(message);
    }
}
