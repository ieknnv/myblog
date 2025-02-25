package org.ieknnv.myblog.dto;

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
public class BlogPostPreviewDto {
    private Integer id;
    private String name;
    private String textPreview;
    private long numberOfLikes;
    private long numberOfComments;
    private String tagsSeparatedByComma;
}
