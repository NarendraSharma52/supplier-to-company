package com.supplify.supplier_to_company.services;

import com.supplify.supplier_to_company.dtos.UserLoginDto;
import com.supplify.supplier_to_company.exceptions.InvalidCredentialsException;
import com.supplify.supplier_to_company.exceptions.UserNotFoundException;
import com.supplify.supplier_to_company.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    UserService userService;

    public String authenticateUser(UserLoginDto userLoginDto) {
        String email = userLoginDto.getEmail();
        // We need to check that this email exists in our user table or not.
        // AuthService -> UserService -> UserRepository
        User user = userService.findByEmail(email);
        if (user == null) {
            throw new UserNotFoundException(String.format("User with id %s does not exist in the system.", userLoginDto.getEmail()));
        }
        if (!userLoginDto.getPassword().equals(user.getPassword())) {
            throw new InvalidCredentialsException(String.format("User entered wrong password"));
        }
    }

}
