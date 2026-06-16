package com.example.couponService.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "issued_coupon",
        uniqueConstraints = @UniqueConstraint(columnNames = {"coupon_id", "user_id"}))
@Getter
@Setter
@NoArgsConstructor
public class IssuedCoupon {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "coupon_id", nullable = false)
    private Coupon coupon;

    private Long userId;

    private Status status;

    private LocalDateTime issuedAt;

    private LocalDateTime expiredAt;

    private LocalDateTime usedAt;

    public static IssuedCoupon create(Coupon coupon, Long userId, LocalDateTime issuedAt, LocalDateTime expiredAt) {
        IssuedCoupon issuedCoupon = new IssuedCoupon();
        issuedCoupon.setCoupon(coupon);
        issuedCoupon.setUserId(userId);
        issuedCoupon.setStatus(Status.ISSUED);
        issuedCoupon.setIssuedAt(issuedAt);
        issuedCoupon.setExpiredAt(expiredAt);
        return issuedCoupon;
    }
}
