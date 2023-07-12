package com.example.blog.controller;

import com.example.blog.dto.BaseResponse;
import com.example.blog.dto.request.*;
import com.example.blog.service.BlogService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Api
@RestController
@RequiredArgsConstructor
public class BlogController {

    private final BlogService service;

    @ApiOperation("Save Blog")
    @PostMapping("/blog/save")
    public BaseResponse<?> saveBlog(@RequestBody @Valid SaveBlogRequest request) {
        return service.saveBlog(request);
    }

    @ApiOperation("Get Blog By Id")
    @PostMapping("/blog/get")
    public BaseResponse<?> getBlogById(@RequestBody @Valid GetBlogByIdRequest request) {
        return service.getBlogPostById(request);
    }

    @ApiOperation("Get All Blog Pagination")
    @PostMapping("/blog/get/all")
    public BaseResponse<?> getAllBlog(@RequestBody @Valid GetAllBlogRequest request) {
        return service.getAllBlog(request);
    }

    @ApiOperation("Update Blog")
    @PostMapping("/blog/update")
    public BaseResponse<?> updateBlogById(@RequestBody @Valid UpdateBlogRequest request) {
        return service.updateBlogById(request);
    }

    @ApiOperation("Delete Blog")
    @PostMapping("/blog/delete")
    public BaseResponse<?> deleteBlog(@RequestBody @Valid DeleteBlogByIdRequest request) {
        return service.deleteBlogById(request);
    }
}
