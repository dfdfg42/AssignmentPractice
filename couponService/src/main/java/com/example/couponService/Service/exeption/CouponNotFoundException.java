package com.example.couponService.Service.exeption;

public class CouponNotFoundException extends RuntimeException {
    public CouponNotFoundException(String message){
        super(message);
    }
}
