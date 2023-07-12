package com.example.blog.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BlogPostDto implements Serializable {
    private static final long serialVersionUID = -1366259773520430084L;

    private Long id;
    private String title;
    private String body;
    private String author;
}
