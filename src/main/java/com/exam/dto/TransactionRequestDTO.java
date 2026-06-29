package com.exam.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public class TransactionRequestDTO {

    @NotNull(message = "금액을 입력하세요.")
    @Min(value = 1, message = "금액은 1원 이상이어야 합니다.")
    private Long amount;

    public TransactionRequestDTO() {
    }

    public TransactionRequestDTO(Long amount) {
        this.amount = amount;
    }

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "TransactionRequestDTO{" +
                "amount=" + amount +
                '}';
    }
}