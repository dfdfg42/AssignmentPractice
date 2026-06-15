package com.example.couponService.repository;

import com.example.couponService.domain.Coupon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CouponRepository extends JpaRepository<Coupon, Integer> {


        //쿠폰 id 로 남은 수량 조회
        @Query()
        public Integer remainCouponQuantity(Long id);



}
