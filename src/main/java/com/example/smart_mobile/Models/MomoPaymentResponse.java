package com.example.smart_mobile.Models;

import lombok.*;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@AllArgsConstructor
@Data
public class MomoPaymentResponse {
    private String requestId;
    private String orderId;
    private String amount;
    private String payUrl;
    private String errorCode;
    private String message;
    private String resultCode;
    // Getters và Setters
}
