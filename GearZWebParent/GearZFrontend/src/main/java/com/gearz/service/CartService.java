package com.gearz.service;

import java.util.List;

import javax.transaction.Transactional;

import com.gearz.common.entity.CartItem;
import com.gearz.common.entity.Customer;
import com.gearz.common.entity.Product;
import com.gearz.repository.CartRepository;
import com.gearz.repository.ProductRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class CartService {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private ProductRepository productRepository;

    public Integer addProduct(Integer productId, Customer customer, Integer quantity) {
        Integer updatedQuantity = quantity;
        Product product = productRepository.findById(productId).get();

        CartItem cart = cartRepository.findByCustomerAndProduct(customer, product);
        if (cart != null) { // Product is already in the cart
            updatedQuantity = cart.getQuantity() + quantity;
            cart.setQuantity(updatedQuantity);
        } else {
            cart = new CartItem();
            cart.setCustomer(customer);
            cart.setProduct(product);
        }

        cart.setQuantity(updatedQuantity);
        cartRepository.save(cart);
        return updatedQuantity;
    }

    public List<CartItem> listCartItems(Customer customer) {
        return cartRepository.findByCustomer(customer);
    }

    public float updateQuantity(Integer productId, Customer customer, Integer quantity) {
        cartRepository.updateQuantity(quantity, customer.getId(), productId);
        Product product = productRepository.findById(productId).get();
        float subtotal = product.getDiscountedPrice() * quantity;
        return subtotal;
    }

    public void removeProductFromCart(Integer productId, Customer customer) {
        cartRepository.deleteByCustomerAndProduct(customer.getId(), productId);
    }

    public void deleteByCustomer(Customer customer) {
        cartRepository.deleteByCustomer(customer.getId());
    }
}
