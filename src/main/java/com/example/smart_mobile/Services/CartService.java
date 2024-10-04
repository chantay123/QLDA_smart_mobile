package com.example.smart_mobile.Services;

import com.example.smart_mobile.Models.Cart;
import com.example.smart_mobile.Models.CartItems;
import com.example.smart_mobile.Models.Product;
import com.example.smart_mobile.Models.User;
import com.example.smart_mobile.Repositories.CartItemsRepository;
import com.example.smart_mobile.Repositories.CartRepository;
import com.example.smart_mobile.Repositories.ProductRepository;
import com.example.smart_mobile.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CartService {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private CartItemsRepository cartItemsRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UserRepository userRepository;

    /**
     * Thêm sản phẩm vào giỏ hàng với số lượng
     */
    public Cart addToCart(Long userId, Long productId) {
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isEmpty()) {
            throw new RuntimeException("User not found");
        }

        User user = userOptional.get();
        Cart cart = user.getCart();

        // Nếu giỏ hàng chưa tồn tại, tạo mới
        if (cart == null) {
            cart = new Cart();
            cart.setUser(user);
            cart = cartRepository.save(cart);
        }

        Optional<Product> productOptional = productRepository.findById(productId);
        if (productOptional.isEmpty()) {
            throw new RuntimeException("Product not found");
        }

        Product product = productOptional.get();

        // Kiểm tra sản phẩm đã tồn tại trong giỏ hàng chưa
        Optional<CartItems> existingCartItem = cart.getCartItems().stream()
                .filter(item -> item.getProduct().getId().equals(productId))
                .findFirst();

        if (existingCartItem.isPresent()) {
            // Nếu đã tồn tại, cập nhật số lượng
            CartItems cartItem = existingCartItem.get();
            cartItem.setQuantity(cartItem.getQuantity() + 1); // Tăng số lượng lên 1
            cartItemsRepository.save(cartItem);
        } else {
            // Nếu chưa có, thêm sản phẩm mới vào giỏ hàng với số lượng là 1
            CartItems newCartItem = new CartItems();
            newCartItem.setCart(cart);
            newCartItem.setProduct(product);
            newCartItem.setQuantity(1); // Mặc định số lượng là 1
            cartItemsRepository.save(newCartItem);
        }

        return cart;
    }

    /**
     * Xóa sản phẩm khỏi giỏ hàng
     */
    @Transactional
    public void removeFromCart(Long userId, Long productId) {
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isEmpty()) {
            throw new RuntimeException("User not found");
        }

        User user = userOptional.get();
        Cart cart = user.getCart();

        if (cart != null) {
            List<CartItems> cartItems = cart.getCartItems();
            Optional<CartItems> itemToRemove = cartItems.stream()
                    .filter(item -> item.getProduct().getId().equals(productId))
                    .findFirst();

            // Nếu tìm thấy sản phẩm, xóa nó
            if (itemToRemove.isPresent()) {
                cartItems.remove(itemToRemove.get());
                cartItemsRepository.delete(itemToRemove.get()); // Xóa khỏi repository
            }
        }
    }


    /**
     * Lấy giỏ hàng của người dùng
     */
    public Cart getCartByUserId(Long userId) {
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isEmpty()) {
            throw new RuntimeException("User not found");
        }

        User user = userOptional.get();
        Cart cart = user.getCart();

        if (cart == null) {
            cart = new Cart();
            cart.setUser(user);
            cart.setCartItems(new ArrayList<>()); // Khởi tạo danh sách rỗng
            cart = cartRepository.save(cart);
        }

        return cart;
    }

    public List<CartItems> getItemsInCart(Long userId) {
        Cart cart = getCartByUserId(userId);
        if (cart != null) {
            return cartItemsRepository.findByCartIdAndCartUserId(cart.getId(), userId);
        }
        return null;
    }

    @Transactional
    public void clearCart(Long userId) {
        Cart cart = getCartByUserId(userId);
        if (cart != null) {
            cartItemsRepository.deleteByCart(cart); // Xóa tất cả các mục trong giỏ hàng
        }
    }

}
