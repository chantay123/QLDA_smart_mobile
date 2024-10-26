package com.example.smart_mobile.Services;

import com.example.smart_mobile.Models.Cart;
import com.example.smart_mobile.Models.CartItems;
import com.example.smart_mobile.Models.MomoPaymentRequest;
import com.example.smart_mobile.Models.MomoPaymentResponse;
import com.example.smart_mobile.Models.Order;
import com.example.smart_mobile.Models.User;
import com.example.smart_mobile.Repositories.OrderRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class PaymentWithMomoService {

    @Autowired
    private UserService userService;
    @Autowired
    private CartService cartService;
    @Autowired
    private OrderService orderService;
    @Autowired
    private OrderRepository orderRepository;

    public MomoPaymentResponse PaymentWithMomo(String username, Long id) throws Exception {
        Optional<User> user = userService.getUserByUsername(username);
        Order order = orderService.getOrderById(id);
        MomoPaymentRequest request = new MomoPaymentRequest();
        request.setPartnerCode("MOMO");
        request.setAccessKey("F8BBA842ECF85");
        request.setRequestId(UUID.randomUUID().toString());
        request.setAmount(Long.toString(order.getTotal()));
        request.setOrderId(UUID.randomUUID().toString());
        request.setOrderInfo("Payment for Order " + request.getOrderId());
        request.setReturnUrl("http://localhost:8088/bill/pay");
        request.setNotifyUrl("https://momo.vn");
        request.setRequestType("payWithMethod");
        request.setRedirectUrl("http://localhost:8088/bill/pay");

        request.setIpnUrl("http://localhost:8088/ipn"); // Thay đổi URL này cho phù hợp với ứng dụng của bạn
        request.setExtraData(order.getId().toString());

        // Tạo chuỗi dữ liệu để ký
        String rawData = "accessKey=" + request.getAccessKey() +
                "&amount=" + request.getAmount() +
                "&extraData=" + request.getExtraData() + // Đảm bảo extraData không null
                "&ipnUrl=" + request.getIpnUrl() + // Đảm bảo ipnUrl không null
                "&orderId=" + request.getOrderId() +
                "&orderInfo=" + request.getOrderInfo() +
                "&partnerCode=" + request.getPartnerCode() +
                "&redirectUrl=" + request.getRedirectUrl() + // Đảm bảo redirectUrl không null
                "&requestId=" + request.getRequestId() +
                "&requestType=" + request.getRequestType();

        // Tạo chữ ký
        request.setSignature(signHmacSHA256(rawData, "K951B6PE1waDMi640xX08PD3vg6EkVlz"));

        // Tạo RestTemplate để gửi yêu cầu
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");

        ObjectMapper mapper = new ObjectMapper();
        String requestBody = mapper.writeValueAsString(request); // Chuyển đổi đối tượng thành chuỗi JSON

        HttpEntity<String> entity = new HttpEntity<>(requestBody, headers);

        // Gửi yêu cầu đến MoMo
        ResponseEntity<MomoPaymentResponse> response = restTemplate.exchange(
                "https://test-payment.momo.vn/v2/gateway/api/create", // URL
                HttpMethod.POST, // Phương thức POST
                entity, // Entity chứa body và headers
                MomoPaymentResponse.class // Phản hồi mong đợi
        );

        MomoPaymentResponse paymentResponse = response.getBody();

        return paymentResponse; // Trả về phản hồi
    }

    public static String signHmacSHA256(String data, String secretKey) throws Exception {
        Mac sha256Hmac = Mac.getInstance("HmacSHA256");
        SecretKeySpec secretKeySpec = new SecretKeySpec(secretKey.getBytes("UTF-8"), "HmacSHA256");
        sha256Hmac.init(secretKeySpec);

        // Tạo chữ ký và chuyển đổi sang định dạng hex
        byte[] signatureBytes = sha256Hmac.doFinal(data.getBytes("UTF-8"));
        StringBuilder hexString = new StringBuilder();
        for (byte b : signatureBytes) {
            String hex = Integer.toHexString(0xff & b);
            if (hex.length() == 1) hexString.append('0');
            hexString.append(hex);
        }
        return hexString.toString();
    }
}