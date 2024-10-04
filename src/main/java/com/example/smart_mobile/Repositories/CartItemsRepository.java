package com.example.smart_mobile.Repositories;

import com.example.smart_mobile.Models.Cart;
import com.example.smart_mobile.Models.CartItems;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartItemsRepository extends JpaRepository<CartItems, Long> {
    void deleteByCart(Cart cart);
    List<CartItems> findByCartIdAndCartUserId(Long cartId, Long userId);
}
