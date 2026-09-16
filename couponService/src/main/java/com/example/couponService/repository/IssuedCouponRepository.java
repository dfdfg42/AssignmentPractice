package com.example.couponService.repository;

import com.example.couponService.domain.IssuedCoupon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IssuedCouponRepository extends JpaRepository<IssuedCoupon, Long> {

    boolean existsByCouponIdAndUserId(Long couponId, Long userId);

}
