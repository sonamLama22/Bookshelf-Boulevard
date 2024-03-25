package com.sonam.ecommerce.ecommercebackend.service.implementation;

import com.sonam.ecommerce.ecommercebackend.dto.AddToCartRequest;
import com.sonam.ecommerce.ecommercebackend.entity.Book;
import com.sonam.ecommerce.ecommercebackend.entity.Cart;
import com.sonam.ecommerce.ecommercebackend.entity.CartItem;
import com.sonam.ecommerce.ecommercebackend.entity.User;
import com.sonam.ecommerce.ecommercebackend.exception.ResourceNotFoundException;
import com.sonam.ecommerce.ecommercebackend.repository.CartItemRepo;
import com.sonam.ecommerce.ecommercebackend.repository.CartRepo;
import com.sonam.ecommerce.ecommercebackend.repository.UserRepo;
import com.sonam.ecommerce.ecommercebackend.service.BookService;
import com.sonam.ecommerce.ecommercebackend.service.CartItemService;
import com.sonam.ecommerce.ecommercebackend.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Service
public class CartServiceImpl implements CartService {

    @Autowired
    CartRepo cartRepo;

    @Autowired
    CartItemRepo cartItemRepo;
    @Autowired
    CartItemService cartItemService;
    @Autowired
    BookService bookService;

    @Autowired
    UserRepo userRepo;


    @Override
    public Cart createCart(User user) {

        Cart cart = new Cart();
        cart.setUser(user);
        return cartRepo.save(cart);
    }

    @Override
    public String addCartItem(int userId, AddToCartRequest request) throws ResourceNotFoundException {
        Cart cart = cartRepo.findByUserUserId(userId);
        Book book = bookService.bookExists(request.getBookId());

        CartItem isPresent = cartItemService.cartItemExists(cart, book, userId);
        if(isPresent == null){
            CartItem cartItem = CartItem.builder()
                    .book(book)
                    .cart(cart)
                    .quantity(request.getQuantity())
                    .userId(userId)
                    .build();
            int price = request.getQuantity() * book.getPrice();
            cartItem.setPrice(price);

            CartItem createdCartItem = cartItemService.createCartItem(cartItem);
            cart.getCartItems().add(createdCartItem);
        }
        return "Item added to a cart.";
    }

    @Override
    public Cart getCart(int userId) {

        User user = userRepo.findById(userId).get();
        Cart cart = cartRepo.findByUserUserId(user.getUserId());
        return cart;
    }


    @Override
    public void removeCart(int cartId) throws ResourceNotFoundException {
        Cart cart = cartRepo.findById(cartId).orElseThrow(()-> new ResourceNotFoundException("Cart not found for this id::"+ cartId));
        // Convert List to Iterable
        Iterable<CartItem> cartItemsIterable = new ArrayList<>(cart.getCartItems());
        // Use Iterable in the deleteAll method
        cartItemRepo.deleteAll(cartItemsIterable);
        cartRepo.delete(cart);
    }


    @Override
    public void updateCartTotals(Cart cart) {
        int totalItem = 0;
        double totalPrice = 0.0;

        for (CartItem cartItem : cart.getCartItems()) {
            totalItem += cartItem.getQuantity();
            totalPrice += cartItem.getPrice();
        }

        cart.setTotalItem(totalItem);
        cart.setTotalPrice(totalPrice);

        cartRepo.save(cart);
    }


}
