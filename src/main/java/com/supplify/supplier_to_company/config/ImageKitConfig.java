package com.supplify.supplier_to_company.config;

import io.imagekit.sdk.ImageKit;
import io.imagekit.sdk.config.Configuration;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;

public class ImageKitConfig {
    @Value("${image.kit.public.key}")
    String publicKey;
    @Value("${image.kit.private.key}")
    String privateKey;
    @Value("${image.kit.url}")
    String url;

    @Bean
    public ImageKit imageKit(){
        ImageKit imageKit = ImageKit.getInstance();
        Configuration configuration = new Configuration(
                publicKey,
                privateKey,
                url
        );
        imageKit.setConfig(configuration);
        return imageKit;
    }

}
