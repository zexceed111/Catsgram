package ru.yandex.practicum.catsgram.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.yandex.practicum.catsgram.model.Image;
import ru.yandex.practicum.catsgram.model.ImageData;
import ru.yandex.practicum.catsgram.service.ImageService;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ImageController {
    private final ImageService imageService;

    @GetMapping("/posts/{postId}/images")
    public List<Image> getPostImages(@PathVariable("postId") long postId){
        return imageService.getPostImages(postId);
    }

    @GetMapping(value = "/images/{imageId}", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public ResponseEntity<byte[]> downloadImage(@PathVariable long imageId) {
        ImageData imageData = imageService.getImageData(imageId);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentDisposition(
                ContentDisposition.attachment()
                        .filename(imageData.getName())
                        .build()
        );

        return new ResponseEntity<>(imageData.getData(), headers, HttpStatus.OK);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("posts/{postID}/images")
    public List<Image> addPostImages(@PathVariable("postdId") long postId,
                                     @RequestParam("image")List <MultipartFile> files){
        return imageService.saveImages(postId, files);
    }
}
