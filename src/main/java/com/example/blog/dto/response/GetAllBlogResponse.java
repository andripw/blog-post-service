package com.example.blog.dto.response;

import com.example.blog.dto.BlogPostDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetAllBlogResponse implements Serializable {
    private static final long serialVersionUID = -3356748224546092406L;

    private List<BlogPostDto> blogPostDtoList;
    private Integer totalPage;
    private Long totalElements;
}
