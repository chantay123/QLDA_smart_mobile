package com.example.smart_mobile.Services;

import com.example.smart_mobile.Models.*;
import com.example.smart_mobile.Repositories.*;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderItemsRepository orderItemsRepository;

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CartItemsRepository cartItemsRepository;
    @Autowired
    private CartRepository cartRepository;

    public List<Order> getAllOrderByIdUser(Long idUser) {
        return orderRepository.findByUserId(idUser);
    }
    public Order getOrderById(Long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found"));
        return order;
    }

    public List<OrderItems> getAllItemsByOrderId(Long orderId) {
        return orderItemsRepository.findByOrderId(orderId);
    }

    @Transactional
    public Order createOrder(Long idUser) {
        Cart cart = cartRepository.findByUserId(idUser);
        List<CartItems> cartItems = cartItemsRepository.findByCartIdAndCartUserId(cart.getId(), idUser);
        int total = cartItems.stream()
                .mapToInt(cartItem -> cartItem.getProduct().getPrice() * cartItem.getQuantity())
                .sum();
        User user = userRepository.findById(idUser).get();
        // Tạo đơn hàng mới
        Order order = new Order();
        order.setUser(user);
        order.setOrderDate(new Date());
        order.setTotal(total);
        order.setAddress(user.getAddress());
        order.setStatus("Unpaid");

        Order savedOrder = orderRepository.save(order);

        for (CartItems cartItem : cartItems) {
            OrderItems orderItem = new OrderItems();
            orderItem.setOrder(savedOrder);
            orderItem.setProduct(cartItem.getProduct());
            orderItem.setPrice(cartItem.getProduct().getPrice());
            orderItem.setQuantity(cartItem.getQuantity());
            orderItemsRepository.save(orderItem);
        }
        return savedOrder;
    }

    @Transactional
    public Order payOrder(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        if ("Paid".equals(order.getStatus())) {
            throw new RuntimeException("Order is already paid");
        }
        order.setStatus("Paid");
        return orderRepository.save(order);
    }
    
}
