package com.example.couponService.Service;


import com.example.couponService.domain.Coupon;
import com.example.couponService.repository.CouponRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;


@RequiredArgsConstructor
public class CouponIssueQueueService {

    private final CouponRepository couponRepository;
    Queue<Long> couponIssueQueue = new ConcurrentLinkedQueue<>();
    ConcurrentHashMap<Long, Long> couponIssueMap = new ConcurrentHashMap<>();

    public void couponIssueQueueEnqueue(Long couponId) {
        couponIssueQueue.add(couponId);
    }

    @Scheduled(initialDelay = 5000, fixedDelay = 5000)
    public void scheduled() {

        if(couponIssueQueue.isEmpty()) {
            return;
        }else{

            while(!couponIssueQueue.isEmpty()) {

                Long couponId = couponIssueQueue.poll();

                Long remain = couponRepository.remainCouponQuantity(couponId);

                if(remain > 0) {

                }

            }

        }


    }
    //쿠폰 요청이 들어옴

    //발급 받을 수 있으면 큐에 넣기

    //발급안되면 fail 반환

}
