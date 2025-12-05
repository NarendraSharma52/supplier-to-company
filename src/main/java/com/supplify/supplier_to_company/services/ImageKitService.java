package com.supplify.supplier_to_company.services;

import com.supplify.supplier_to_company.exceptions.UploadFileException;
import io.imagekit.sdk.ImageKit;
import io.imagekit.sdk.exceptions.*;
import io.imagekit.sdk.models.FileCreateRequest;
import io.imagekit.sdk.models.results.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Base64;

@Slf4j
@Service
public class ImageKitService {

    @Autowired
    private ImageKit imageKit;


    public Result uploadFile(MultipartFile file, String fileName, String folder) {
        try{
            byte [] fileBytes = file.getBytes();
            String base64 = Base64.getEncoder().encodeToString(fileBytes);
            FileCreateRequest fileCreateRequest = new FileCreateRequest(
                    base64,
                    fileName
            );
            fileCreateRequest.setFolder(folder);
            fileCreateRequest.setUseUniqueFileName(true);
            Result result = imageKit.upload(fileCreateRequest);
            return result;
        }catch (Exception e){
            log.error("Error uploading file in Imagekit : " + e.getMessage());
            throw new UploadFileException(e.getMessage());
        }
    }

}