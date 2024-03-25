package com.sonam.ecommerce.ecommercebackend.service.implementation;

import com.sonam.ecommerce.ecommercebackend.entity.Book;
import com.sonam.ecommerce.ecommercebackend.entity.Cart;
import com.sonam.ecommerce.ecommercebackend.entity.CartItem;
import com.sonam.ecommerce.ecommercebackend.entity.User;
import com.sonam.ecommerce.ecommercebackend.exception.ResourceNotFoundException;
import com.sonam.ecommerce.ecommercebackend.repository.CartItemRepo;
import com.sonam.ecommerce.ecommercebackend.repository.CartRepo;
import com.sonam.ecommerce.ecommercebackend.service.CartItemService;
import com.sonam.ecommerce.ecommercebackend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CartItemServiceImpl implements CartItemService {

    @Autowired
    private CartItemRepo cartItemRepo;
    @Autowired
    private UserService userService;
    @Autowired
    private CartRepo cartRepo;


    @Override
    public CartItem createCartItem(CartItem cartItem) {
        cartItem.setQuantity(1);
        cartItem.setPrice(cartItem.getBook().getPrice()*cartItem.getQuantity());
        return cartItemRepo.save(cartItem);
    }

    @Override
    public CartItem updateCartItem(int userId, int id, CartItem cartItem) throws ResourceNotFoundException {
        CartItem item = findCartItemById(id);
        User user = userService.findUser(item.getUserId());

        if(user.getUserId() == userId){
            item.setQuantity(cartItem.getQuantity());
            item.setPrice(item.getQuantity()*item.getBook().getPrice());

        }
        return cartItemRepo.save(item);
    }

    @Override
    public CartItem cartItemExists(Cart cart, Book book, int userId) {
        CartItem cartItem = cartItemRepo.cartItemExists(cart, book, userId);
        return cartItem;
    }

    @Override
    public void removeCartItem(int cartId, int cartItemId) throws ResourceNotFoundException {
        CartItem cartItem = findCartItemById(cartItemId);
        User user = userService.findUser(cartItem.getUserId());
       Cart cart = user.getCart();
        if(cart != null && cart.getId() == cartId){
            cartItemRepo.deleteById(cartItemId);
        }
        else{
            throw new ResourceNotFoundException("Cannot delete another user's item.");
        }
    }


    @Override
    public CartItem findCartItemById(int cartItemId) throws ResourceNotFoundException {
        Optional<CartItem> cartItemOptional = cartItemRepo.findById(cartItemId);
        if(cartItemOptional.isPresent()){
            return cartItemOptional.get();
        }
        throw new ResourceNotFoundException("Cart item not found for id::"+cartItemId);
    }
}
