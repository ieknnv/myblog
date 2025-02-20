package org.ieknnv.myblog.mapper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.ieknnv.myblog.dto.BlogPostDto;
import org.ieknnv.myblog.dto.BlogPostPreviewDto;
import org.ieknnv.myblog.model.BlogPost;

public final class BlogPostMapper {

    private BlogPostMapper() {
    }

    public static BlogPost toModel(BlogPostDto dto) throws IOException {
        return new BlogPost(dto.getId(), dto.getName(), dto.getFile().getBytes(), dto.getText(),
                dto.getNumberOfLikes(), getTags(dto));
    }

    public static BlogPostPreviewDto toPreviewDto(BlogPost model) {
        BlogPostPreviewDto dto = new BlogPostPreviewDto();
        dto.setId(model.getId());
        dto.setName(model.getName());
        dto.setTextPreview(model.getText()); // TODO preview
        dto.setNumberOfComments(0); // TODO comments
        dto.setNumberOfLikes(model.getNumberOfLikes());
        dto.setTagsSeparatedByComma(StringUtils.join(model.getTags(), ", "));
        return dto;
    }

    private static List<String> getTags(BlogPostDto dto) {
        List<String> tags = new ArrayList<>();
        if (!StringUtils.isEmpty(dto.getTagsSeparatedByComma())) {
            String[] tagsArray = dto.getTagsSeparatedByComma().replaceAll(StringUtils.SPACE, StringUtils.EMPTY)
                    .split(",");
            for (String tag : tagsArray) {
                if (tag.startsWith("#")) {
                    tags.add(tag);
                } else {
                    tags.add("#" + tag);
                }
            }
        }
        return tags;
    }
}
