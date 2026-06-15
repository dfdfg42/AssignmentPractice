package com.example.couponService.controller;

import ch.qos.logback.core.model.Model;
import com.example.couponService.domain.Coupon;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Controller
public class CouponIssueController {


    @GetMapping("/api/coupons/available")
    public List<AvailableCouponDTO>() {



    }

    @PostMapping("/api/coupons/{couponId}/issue")
    public String issueCoupon(@PathVariable Long couponId, Model model) {

        String jobId = UUID.randomUUID().toString();



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
}
