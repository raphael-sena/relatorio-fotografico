package com.newenergy.inspecao_rei.services;

import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Base64;

@Service
public class ImageService {

    public String encodeImageToBase64(String imagePath) throws IOException {
        File file = new File(imagePath);
        FileInputStream fis = new FileInputStream(file);
        byte[] imageBytes = fis.readAllBytes();
        fis.close();
        return Base64.getEncoder().encodeToString(imageBytes);
    }

    public byte[] decodeBase64Image(String base64Image) {
        return Base64.getDecoder().decode(base64Image);
    }
}
