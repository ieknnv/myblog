package org.ieknnv.myblog.mapper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.ieknnv.myblog.dto.BlogPostDto;
import org.ieknnv.myblog.dto.BlogPostPreviewDto;
import org.ieknnv.myblog.model.BlogPost;

public final class BlogPostMapper {

    private static final int NUMBER_OF_PREVIEW_SENTENCES = 3;

    private BlogPostMapper() {
    }

    public static BlogPost toModel(BlogPostDto dto) throws IOException {
        return new BlogPost(dto.getId(), dto.getName(), dto.getFile().getBytes(), dto.getText(),
                dto.getNumberOfLikes(), getTags(dto));
    }

    public static BlogPostDto toDto(BlogPost model) {
        BlogPostDto dto = new BlogPostDto();
        dto.setId(model.getId());
        dto.setName(model.getName());
        dto.setFile(null);
        dto.setImage(model.getImage());
        dto.setText(model.getText());
        dto.setNumberOfLikes(model.getNumberOfLikes());
        dto.setTagsSeparatedByComma(StringUtils.join(model.getTags(), ", "));
        dto.setTags(model.getTags());
        return dto;
    }

    public static BlogPostPreviewDto toPreviewDto(BlogPost model) {
        BlogPostPreviewDto dto = new BlogPostPreviewDto();
        dto.setId(model.getId());
        dto.setName(model.getName());
        dto.setTextPreview(getPreview(model.getText()));
        dto.setNumberOfComments(0); // TODO comments
        dto.setNumberOfLikes(model.getNumberOfLikes());
        dto.setTagsSeparatedByComma(StringUtils.join(model.getTags(), ", "));
        return dto;
    }

    private static String getPreview(String text) {
        if (StringUtils.isEmpty(text)) {
            return StringUtils.EMPTY;
        }

        String[] sentences = text.split("(?<=[.!?])\\s+");

        if (sentences.length <= NUMBER_OF_PREVIEW_SENTENCES) {
            return text;
        }

        StringBuilder preview = new StringBuilder();
        for (int i = 0; i < NUMBER_OF_PREVIEW_SENTENCES; i++) {
            preview.append(sentences[i]).append(" ");
        }

        return preview.toString().trim() + " ...";
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
