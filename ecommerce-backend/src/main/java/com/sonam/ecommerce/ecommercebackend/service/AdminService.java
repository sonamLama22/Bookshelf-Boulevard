package com.sonam.ecommerce.ecommercebackend.service;

import com.sonam.ecommerce.ecommercebackend.entity.Admin;

public interface AdminService {

    public Admin register(Admin admin);
    public Admin login(Admin admin);

}
