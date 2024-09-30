package com.farmstory.controllerAdvice;

import com.farmstory.apiController.OrderController;
import com.farmstory.exception.OutOfStockException;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
@Order(1)
@ControllerAdvice(assignableTypes = OrderController.class)
public class OrderExceptionHandler {
    // 주문 예외 처리
    @ExceptionHandler(OutOfStockException.class)
    public ResponseEntity<ErrorResponseDTO> handleAllExceptions(OutOfStockException ex) {
        String errorMessage = StringUtils.hasText(ex.getMessage()) ? ex.getMessage() : "상품의 재고가 부족합니다.";

        ErrorResponseDTO errorResponse = new ErrorResponseDTO(
                HttpStatus.BAD_REQUEST.value(),
                "OutOfStock",
                errorMessage
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }
}
