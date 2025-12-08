package com.supplify.supplier_to_company.services;

import com.supplify.supplier_to_company.models.User;
import com.supplify.supplier_to_company.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    public User findByEmail(String email){
        return userRepository.findByEmail(email);
    }



}
