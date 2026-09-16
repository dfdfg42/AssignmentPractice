package com.example.couponService.Service.exeption;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CouponNotFoundException.class)
    public ResponseEntity<?> handleNotFound(CouponNotFoundException ex) {
        return ResponseEntity.status(404).body(new ErrorResponse(false, ex.getMessage()));
    }

    @ExceptionHandler({CouponExhaustedException.class, DuplicateCouponIssueException.class})
    public ResponseEntity<?> handleConflict(RuntimeException ex) {
        return ResponseEntity.status(409).body(new ErrorResponse(false, ex.getMessage()));
    }

    @ExceptionHandler(CouponNotAvailableException.class)
    public ResponseEntity<?> handleBadRequest(CouponNotAvailableException ex) {
        return ResponseEntity.badRequest().body(new ErrorResponse(false, ex.getMessage()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleOther(Exception ex) {
        return ResponseEntity.status(500).body(new ErrorResponse(false, "서버 오류"));
    }

    private static class ErrorResponse {
        private final boolean success;
        private final String message;

        public ErrorResponse(boolean success, String message) {
            this.success = success;
            this.message = message;
        }

        public boolean isSuccess() { return success; }
        public String getMessage() { return message; }
    }
}
