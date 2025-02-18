package org.ieknnv.myblog.mapper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.ieknnv.myblog.dto.BlogPostDto;
import org.ieknnv.myblog.model.BlogPost;

public final class BlogPostMapper {

    private BlogPostMapper() {
    }

    public static BlogPost toModel(BlogPostDto dto) throws IOException {
        byte[] bytes = dto.getFile().getBytes();
        String imageBase64 = Base64.getEncoder().encodeToString(bytes);
        return new BlogPost(dto.getId(), dto.getName(), imageBase64, dto.getText(), getTags(dto));
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
