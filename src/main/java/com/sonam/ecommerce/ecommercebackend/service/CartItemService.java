package com.sonam.ecommerce.ecommercebackend.service;

import com.sonam.ecommerce.ecommercebackend.entity.Book;
import com.sonam.ecommerce.ecommercebackend.entity.Cart;
import com.sonam.ecommerce.ecommercebackend.entity.CartItem;
import com.sonam.ecommerce.ecommercebackend.entity.User;
import com.sonam.ecommerce.ecommercebackend.exception.ResourceNotFoundException;

import java.util.List;

public interface CartItemService {
    public CartItem createCartItem(CartItem cartItem);
    public CartItem updateCartItem(int userId, int id, CartItem cartItem) throws ResourceNotFoundException;
    public CartItem cartItemExists(Cart cart, Book book, int userId);
    public void removeCartItem(int cartId, int cartItemId) throws ResourceNotFoundException;
    public CartItem findCartItemById(int cartItemId) throws ResourceNotFoundException;

    public List<CartItem> getCartItemsForUser(User user);
}
