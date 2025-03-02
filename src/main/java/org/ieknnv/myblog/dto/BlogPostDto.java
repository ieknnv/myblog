package org.ieknnv.myblog.dto;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class BlogPostDto {
    private Integer id;
    private String name;
    private MultipartFile file;
    private byte[] image;
    private String text;
    private long numberOfLikes;
    private String tagsSeparatedByComma;
    private List<String> tags;
}