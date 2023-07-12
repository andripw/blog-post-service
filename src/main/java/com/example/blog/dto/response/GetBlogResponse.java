package com.example.blog.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetBlogResponse implements Serializable {
    private static final long serialVersionUID = -6692643534636777641L;

    private Long id;
    private String title;
    private String body;
    private String author;
}
