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
public class DeleteBlogByIdRequest implements Serializable {
    private static final long serialVersionUID = 2445989998109685715L;

    private Long id;
}
