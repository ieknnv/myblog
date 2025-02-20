package org.ieknnv.myblog.model;

import java.util.List;

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
public class BlogPost {
    private Integer id;
    private String name;
    private byte[] image;
    private String text;
    private long numberOfLikes;
    private List<String> tags;

    public BlogPost(Integer id, String name, byte[] image, String text, long numberOfLikes) {
        this.id = id;
        this.name = name;
        this.image = image;
        this.text = text;
        this.numberOfLikes = numberOfLikes;
    }
}