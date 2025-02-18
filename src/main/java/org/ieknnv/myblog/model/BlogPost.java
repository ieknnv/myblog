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
    private String base64Image;
    private String text;
    private List<String> tags;
}
