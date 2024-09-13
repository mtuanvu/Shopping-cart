package com.shoppingCart.Shopping_cart.service.image;

import com.shoppingCart.Shopping_cart.dto.ImageDto;
import com.shoppingCart.Shopping_cart.model.Image;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface IImageService {
    Image getImageById(Long id);

    void deleteImageById(Long id);

    List<ImageDto> saveImages(List<MultipartFile> files, Long productId);

    void updateImage(MultipartFile file, Long imageId);
}
