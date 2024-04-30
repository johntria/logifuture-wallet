package com.logifuture.wallet.dto.error;

import lombok.Builder;

@Builder
public record ErrorDTO(String code, String message) {
}