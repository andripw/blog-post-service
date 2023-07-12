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
public class SaveBlogRequest implements Serializable {
    private static final long serialVersionUID = 4773356935142325652L;

    private String title;
    private String body;
    private String author;
}
