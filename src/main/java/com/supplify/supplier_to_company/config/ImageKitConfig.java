package com.supplify.supplier_to_company.config;

import io.imagekit.sdk.ImageKit;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ImageKitConfig {

    @Value("${image.kit.public.key}")
    private String publicKey;

    @Value("${image.kit.private.key}")
    private String privateKey;

    @Value("${image.kit.url}")
    private String url;

    @Bean
    public ImageKit imageKit() {
        ImageKit imageKit = ImageKit.getInstance();

        io.imagekit.sdk.config.Configuration imageKitConfig =
                new io.imagekit.sdk.config.Configuration(
                        publicKey,
                        privateKey,
                        url
                );

        imageKit.setConfig(imageKitConfig);
        return imageKit;
    }
}
