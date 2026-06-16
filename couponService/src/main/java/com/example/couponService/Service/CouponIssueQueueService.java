package com.example.couponService.Service;


import com.example.couponService.Service.dto.IssueResult;
import com.example.couponService.Service.dto.IssueTask;
import com.example.couponService.Service.exeption.CouponExhaustedException;
import com.example.couponService.Service.exeption.CouponNotFoundException;
import com.example.couponService.Service.exeption.DuplicateCouponIssueException;
import com.example.couponService.domain.Coupon;
import com.example.couponService.domain.IssuedCoupon;
import com.example.couponService.domain.Status;
import com.example.couponService.repository.CouponRepository;
import com.example.couponService.repository.IssuedCouponRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Queue;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentLinkedQueue;


@RequiredArgsConstructor
@Service
public class CouponIssueQueueService {

    private final CouponRepository couponRepository;
    private final IssuedCouponRepository issuedCouponRepository;

    Queue<IssueTask> couponIssueQueue = new ConcurrentLinkedQueue<>();

    public CompletableFuture<IssueResult> enqueueIssue(Long couponId, Long userId) {
        CompletableFuture<IssueResult> future = new CompletableFuture<>();
        couponIssueQueue.add(new IssueTask(couponId,userId,future));
        return future;
    }

    //쿠폰을 정기적으로 소모하는 워커
    @Scheduled(initialDelay = 5000, fixedDelay = 5000)
    public void scheduled() {
        IssueTask task;

        while((task = couponIssueQueue.poll()) != null) {

            Long couponId = task.getCouponId();
            Long userId = task.getUserId();

            Coupon coupon = couponRepository.findById(couponId)
                    .orElseThrow(() -> new CouponNotFoundException("쿠폰을 찾을 수 없습니다."));

            int remain = coupon.getTotalQuantity() - coupon.getIssuedQuantity();
            if (remain <= 0) {
                task.getFuture().completeExceptionally(new CouponExhaustedException("쿠폰 재고 소진"));
                continue;
            }

            boolean duplicate = issuedCouponRepository.existsByCouponIdAndUserId(couponId, userId);
            if (duplicate) {
                task.getFuture().completeExceptionally(new DuplicateCouponIssueException("이미 발행된 쿠폰입니다."));
                continue;
            }

            IssuedCoupon issuedCoupon = issuedCouponRepository.save(
                    IssuedCoupon.create(coupon, userId, LocalDateTime.now(), coupon.getEndDate())
            );

            task.getFuture().complete(new IssueResult(issuedCoupon));

        }

    }



}
