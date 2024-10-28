package com.example.smart_mobile.Models;


import lombok.*;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@AllArgsConstructor
@Data
public class MomoPaymentRequest {
    private String partnerCode;
    private String accessKey;
    private String requestId;
    private String amount;
    private String orderId;
    private String orderInfo;
    private String returnUrl;
    private String redirectUrl;
    private String notifyUrl;
    private String requestType;
    private String signature;
    private String ipnUrl;
    private String extraData;

    // Getters và Setters
}
