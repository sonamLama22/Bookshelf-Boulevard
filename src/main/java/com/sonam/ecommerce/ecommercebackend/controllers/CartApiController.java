package com.sonam.ecommerce.ecommercebackend.controllers;

import com.sonam.ecommerce.ecommercebackend.dto.AddToCartRequest;
import com.sonam.ecommerce.ecommercebackend.dto.CartDTO;
import com.sonam.ecommerce.ecommercebackend.entity.Cart;
import com.sonam.ecommerce.ecommercebackend.entity.User;
import com.sonam.ecommerce.ecommercebackend.exception.ResourceNotFoundException;
import com.sonam.ecommerce.ecommercebackend.service.UserService;
import com.sonam.ecommerce.ecommercebackend.service.implementation.CartItemServiceImpl;
import com.sonam.ecommerce.ecommercebackend.service.implementation.CartServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class CartApiController {

    @Autowired
    CartServiceImpl cartService;
    @Autowired
    CartItemServiceImpl cartItemService;
    @Autowired
    UserService userService;


    @PutMapping("/user/cart/add-to-cart/{userId}")
    @PreAuthorize("#userId == authentication.principal.userId")
    public ResponseEntity<?> addToCart(@RequestBody AddToCartRequest req, @PathVariable int userId)  throws ResourceNotFoundException {
        try{
            String message = cartService.addCartItem(userId, req);
            Cart cart = cartService.getCart(userId);
            cartService.updateCartTotals(cart);

            return ResponseEntity.ok(message);
        } catch(ResourceNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }


    @GetMapping("/user/cart/{userId}")
    @PreAuthorize("#userId == authentication.principal.userId")
    public ResponseEntity<?> getCart(@PathVariable int userId) {
        Cart cart = cartService.getCart(userId);
        if (cart != null) {
            // Convert the Cart entity to a CartDTO
            CartDTO cartDto = new CartDTO();
            BeanUtils.copyProperties(cart, cartDto);
            return ResponseEntity.ok(cartDto);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/user/cart/{cartId}/items/{itemId}")
    @PreAuthorize("#userId == authentication.principal.userId")
    public ResponseEntity<String> removeItemFromCart(@PathVariable int cartId, @PathVariable int itemId) {
        try {
            cartItemService.removeCartItem(cartId, itemId);
            return ResponseEntity.ok("Item removed from the cart.");
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }


    @DeleteMapping("/user/cart/remove/{cartId}")
    @PreAuthorize("#userId == authentication.principal.userId")
    public ResponseEntity<String> removeCart(@PathVariable int cartId) throws ResourceNotFoundException {
        cartService.removeCart(cartId);
        return ResponseEntity.ok("Cart removed successfully.");
    }

}
