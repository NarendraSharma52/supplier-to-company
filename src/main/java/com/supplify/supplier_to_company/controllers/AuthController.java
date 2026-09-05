package com.supplify.supplier_to_company.controllers;


import com.supplify.supplier_to_company.dtos.AuthResponseDto;
import com.supplify.supplier_to_company.dtos.CreateRoleDto;
import com.supplify.supplier_to_company.dtos.UserLoginDto;
import com.supplify.supplier_to_company.exceptions.InvalidCredentialsException;
import com.supplify.supplier_to_company.exceptions.UnAuthorizedException;
import com.supplify.supplier_to_company.exceptions.UserNotFoundException;
import com.supplify.supplier_to_company.models.Operation;
import com.supplify.supplier_to_company.models.RefreshToken;
import com.supplify.supplier_to_company.models.Role;
import com.supplify.supplier_to_company.models.User;
import com.supplify.supplier_to_company.security.JwtUtil;
import com.supplify.supplier_to_company.services.AuthService;
import com.supplify.supplier_to_company.services.RefreshTokenService;
import com.supplify.supplier_to_company.services.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/s2c/api/v1/auth")
public class AuthController {

    @Autowired
    AuthService authService;

    @Autowired
    JwtUtil jwtUtil;

    @Autowired
    RefreshTokenService refreshTokenService;

    @Autowired
    RoleService roleService;

    @PostMapping("/login")
    public ResponseEntity loginUser(@RequestBody UserLoginDto userLoginDto){
        try{
            AuthResponseDto authResponseDto = authService.authenticateUser(userLoginDto);
            return new ResponseEntity(authResponseDto, HttpStatus.OK);
        }catch (UserNotFoundException e){
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("error", "Invalid Email");
            errorResponse.put("message", e.getMessage());
            return new ResponseEntity(errorResponse, HttpStatus.UNAUTHORIZED); // 401
        }catch (InvalidCredentialsException e){
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("error", "Incorrect Password");
            errorResponse.put("message", e.getMessage());
            return new ResponseEntity(errorResponse, HttpStatus.UNAUTHORIZED);
        }catch (Exception e){
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("error", "Failed to Login due to server side issue");
            errorResponse.put("message", e.getMessage());
            return new ResponseEntity(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/roles")
    public ResponseEntity getOrganisationRolesByUserSession(@RequestHeader String Authorization){
        try{
            List<Role> roles = authService.getAllOrgRolesByUserSession(Authorization);
            return new ResponseEntity<>(roles, HttpStatus.OK);
        }catch (UnAuthorizedException e){
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("error", "UnAuthorized");
            errorResponse.put("message", e.getMessage());
            return new ResponseEntity(errorResponse, HttpStatus.UNAUTHORIZED);
        }catch (Exception e){
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("error", "Failed to fetch roles");
            errorResponse.put("message", e.getMessage());
            return new ResponseEntity(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/operations")
    public  ResponseEntity getAllOperationsByUserSession(@RequestHeader  String Authorization){
        try{
            List<Operation> operations = authService.getAllOperationsByUserSession(Authorization);
            return new ResponseEntity<>(operations, HttpStatus.OK);
        }catch (UnAuthorizedException e){
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("error", "UnAuthorized");
            errorResponse.put("message", e.getMessage());
            return new ResponseEntity(errorResponse, HttpStatus.UNAUTHORIZED);
        }catch (Exception e){
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("error", "Failed to fetch roles");
            errorResponse.put("message", e.getMessage());
            return new ResponseEntity(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }


    }
    @PostMapping("/create-role")
    public ResponseEntity createRole(@RequestHeader String Authorization,
                                     @RequestBody CreateRoleDto createRoleDto){

        try{
            Role role = authService.createRole( createRoleDto,Authorization);
            return new ResponseEntity<>(role, HttpStatus.OK);
        }catch (UnAuthorizedException e){
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("error", "UnAuthorized");
            errorResponse.put("message", e.getMessage());
            return new ResponseEntity(errorResponse, HttpStatus.UNAUTHORIZED);
        }catch (Exception e){
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("error", "Failed to fetch roles");
            errorResponse.put("message", e.getMessage());
            return new ResponseEntity(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
    @PostMapping("/refresh")
    public ResponseEntity refreshToken(@RequestBody RefreshToken request){
       RefreshToken refreshToken= refreshTokenService.Refreshtokenvalidty(request.getRefreshToken());

        User user=refreshToken.getUser();
        List<Role> roles=roleService.getRolesByIds(user.getId());
        String token=jwtUtil.generateJwtToken(user.getEmail(),roles);

        return token;

    }
}








