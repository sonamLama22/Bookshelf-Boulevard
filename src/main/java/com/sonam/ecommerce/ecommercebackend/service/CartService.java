package com.sonam.ecommerce.ecommercebackend.service;

import com.sonam.ecommerce.ecommercebackend.dto.AddToCartRequest;
import com.sonam.ecommerce.ecommercebackend.entity.Cart;
import com.sonam.ecommerce.ecommercebackend.entity.User;
import com.sonam.ecommerce.ecommercebackend.exception.ResourceNotFoundException;

public interface CartService {

    public Cart createCart(User user);
    public String addCartItem(int userId, AddToCartRequest request) throws ResourceNotFoundException;
    public Cart getCart(int userId);
    public void removeCart(int cartId) throws ResourceNotFoundException;
  //  public Cart findCartByUser(int userId);
    public void updateCartTotals(Cart cart);

}
