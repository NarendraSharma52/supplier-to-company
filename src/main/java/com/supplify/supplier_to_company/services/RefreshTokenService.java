package com.supplify.supplier_to_company.services;

import com.supplify.supplier_to_company.models.RefreshToken;
import com.supplify.supplier_to_company.repositories.RefreshTokenRepository;
import com.supplify.supplier_to_company.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.Instant;
import java.util.UUID;

public class RefreshTokenService {

    @Autowired
    private RefreshTokenRepository refreshTokenRepository;

    @Autowired
    private UserRepository userRepository;

    public long Refreshtokenvalidty=5*60*60*1000;

    public RefreshToken create(String user){


        RefreshToken refreshToken1=user.getRefreshToke();

        if(refreshToken1==null){
            RefreshToken refreshToken= RefreshToken.builder()
                    .token(UUID.randomUUID().toString())
                    .expiryDate(Instant.now().plusMillis(Refreshtokenvalidty))
                    .user(userRepository.findByEmail(username))
                    .build();

            refreshTokenRepository.save((refreshToken));
        }

        else{
            refreshToken1.setexpiryDte(Instant.now().plus(refreshToken1));
        }



        return refreshToken1;




    }

    public RefreshToken tokenverify(String refreshToken){
      RefreshToken refreshTokenOb=  refreshTokenRepository.findAllById(refreshToken);

      if(refreshTokenOb.getexpirtDate().Instance.now()<0){
          refreshTokenRepository.delete((refreshToken));
          throw new RuntimeException("INvalid Exception");
      }
      else{
          return RefreshToken
      }


    }


}
