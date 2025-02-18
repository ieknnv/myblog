package org.ieknnv.myblog.dto;

import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class BlogPostDto {
    private Integer id;
    private String name;
    private MultipartFile file;
    private String text;
    private String tagsSeparatedByComma;
}