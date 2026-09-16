package com.example.couponService.repository;

import com.example.couponService.domain.Coupon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CouponRepository extends JpaRepository<Coupon, Long> {

    @Query("SELECT c.totalQuantity - c.issuedQuantity FROM Coupon c WHERE c.id = :couponId")
    Integer remainCouponQuantity(@Param("couponId") Long couponId);

}
