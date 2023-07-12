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
public class GetBlogByIdRequest implements Serializable {
    private static final long serialVersionUID = 2394885881634102767L;

    private Long id;
}
