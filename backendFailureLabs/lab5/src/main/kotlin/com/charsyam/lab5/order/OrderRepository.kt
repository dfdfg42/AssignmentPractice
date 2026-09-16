package com.charsyam.lab5.order

import jakarta.persistence.LockModeType
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Lock

interface OrderRepository : JpaRepository<Order, Long> {

    // 잠금 읽기(SELECT … FOR UPDATE): REPEATABLE READ 의 트랜잭션 스냅샷을 무시하고 최신 커밋값을 읽는다.
    // 지갑 FOR UPDATE 를 기다린 두 번째 요청이 "잠금 전에 찍힌 스냅샷"으로 주문을 읽어 100건을 다시 결제하던
    // 문제(LAB 5)를 막는다 — 잠금 획득 뒤 이 조회는 첫 요청이 바꾼 status 를 본다.
    fun findAllByWalletIdAndStatusOrderById(walletId: Long, status: OrderStatus): List<Order>

    fun countByWalletIdAndStatus(walletId: Long, status: OrderStatus): Long
}
