package org.ieknnv.myblog.controller;

import org.ieknnv.myblog.service.BlogPostService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.server.ResponseStatusException;

@Controller
@RequestMapping("/image")
public class ImageController {

    private final BlogPostService blogPostService;

    public ImageController(BlogPostService blogPostService) {
        this.blogPostService = blogPostService;
    }

    @GetMapping("/post/{id}")
    public ResponseEntity<byte[]> findImage(@PathVariable(name = "id") Integer id) {
        byte[] imageBytes = blogPostService.findImageByPostId(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_PNG);

        return new ResponseEntity<>(imageBytes, headers, HttpStatus.OK);
    }
}
