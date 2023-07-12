package com.example.blog.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateBlogRequest implements Serializable {
    private static final long serialVersionUID = 8896965068336890176L;

    private Long id;
    private String title;
    private String body;
    private String author;

}
