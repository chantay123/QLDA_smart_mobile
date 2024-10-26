package com.example.smart_mobile.Controllers;

import com.example.smart_mobile.Models.MomoPaymentResponse;
import com.example.smart_mobile.Services.PaymentWithMomoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PaymentController {

    @Autowired
    private PaymentWithMomoService paymentWithMomoService;

    @PostMapping("/ipn")
    public ResponseEntity<String> handleIpn(@RequestBody String ipnData) {
        System.out.println("Received IPN data: " + ipnData);
        return ResponseEntity.ok("IPN received successfully");
    }

    @PostMapping("/process-payment")
    public ResponseEntity<MomoPaymentResponse> processPayment(
            @RequestParam Long id
    ) throws Exception {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        MomoPaymentResponse paymentResponse = paymentWithMomoService.PaymentWithMomo(username, id);
        if (paymentResponse != null && paymentResponse.getPayUrl() != null) {
            return ResponseEntity.status(302).header("Location", paymentResponse.getPayUrl()).build(); // Chuyển hướng đến trang thanh toán của MoMo
        } else {
            return ResponseEntity.badRequest().body(paymentResponse);
        }
    }
}
