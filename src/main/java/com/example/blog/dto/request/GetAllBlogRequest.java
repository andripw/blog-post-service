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
public class GetAllBlogRequest implements Serializable {
    private static final long serialVersionUID = -5357140525958245141L;

    private Integer pageNo;
    private Integer pageSize;
}
