package com.supplify.supplier_to_company.services;


import com.supplify.supplier_to_company.dtos.AuthResponseDto;
import com.supplify.supplier_to_company.dtos.UserLoginDto;
import com.supplify.supplier_to_company.exceptions.InvalidCredentialsException;
import com.supplify.supplier_to_company.exceptions.UnAuthorizedException;
import com.supplify.supplier_to_company.exceptions.UserNotFoundException;
import com.supplify.supplier_to_company.models.Operation;
import com.supplify.supplier_to_company.models.Role;
import com.supplify.supplier_to_company.models.Supplier;
import com.supplify.supplier_to_company.models.User;
import com.supplify.supplier_to_company.security.JwtUtil;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class AuthService {

    @Autowired
    UserService userService;

    @Autowired
    JwtUtil jwtUtil;

    @Autowired
    RoleService roleService;

    @Autowired
    SupplierService supplierService;

    public AuthResponseDto authenticateUser(UserLoginDto userLoginDto){
        String email = userLoginDto.getEmail();
        // We need to check that this email exists in our user table or not.
        // AuthService -> UserService -> UserRepository
        User user = userService.findByEmail(email);
        if(user == null){
            throw new UserNotFoundException(String.format("User with id %s does not exist in the system.", userLoginDto.getEmail()));
        }
        if(!userLoginDto.getPassword().equals(user.getPassword())){
            throw new InvalidCredentialsException(String.format("User entered wrong password"));
        }
        AuthResponseDto authResponseDto = new AuthResponseDto();
        authResponseDto.setUserId(user.getId());
        authResponseDto.setEmail(user.getEmail());
        authResponseDto.setUserType(user.getUserType());
        authResponseDto.setExpiresAt(jwtUtil.getExpirationTime());
        authResponseDto.setRoles(user.getRoles());
        List<String> roleNames = roleService.mapRoleToRoleNames(user.getRoles());
        String token = jwtUtil.generateJwtToken(user.getEmail(), roleNames);
        authResponseDto.setToken(token);
        return authResponseDto;
    }
    public List<Role> getAllOrgRolesByUserSession(String token){
        Claims claims = jwtUtil.decryptToken(token);
        String email = claims.get("email", String.class);
        List<String> roles = claims.get("roles", List.class);
        boolean isAccess = this.isAccessAvailable(roles, "see_all_available_roles");
        if(!isAccess){
            throw new UnAuthorizedException(String.format("User is not having access to check all org roles"));
        }
        User user = userService.findByEmail(email);
        if(user.getUserType().equals("SUPPLIER_USER")){
            String orgName = supplierService.getSupplierNameBySupplierUser(user);
            return roleService.getRoleByOrgName(orgName);
        }
        return new ArrayList<>();
    }

    public boolean isAccessAvailable(List<String> userRoles, String operationName){
        for(String roleName : userRoles){
            boolean res = this.checkRoleIsHavingAccessForGivenOperation(roleName, operationName);
            if(res == true){
                return true;
            }
        }
        return false;
    }

    public boolean checkRoleIsHavingAccessForGivenOperation(String roleName, String operationName){
        Role role = roleService.findByName(roleName);
        for(Operation operation : role.getOperations()){
            if(operation.getName().equals(operationName)){
                return true;
            }
        }
        return false;
    }




}
