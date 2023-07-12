package com.example.blog.service;

import com.example.blog.constant.BlogConstant;
import com.example.blog.dto.BaseResponse;
import com.example.blog.dto.BlogPostDto;
import com.example.blog.dto.request.*;
import com.example.blog.dto.response.GetAllBlogResponse;
import com.example.blog.dto.response.GetBlogResponse;
import com.example.blog.dto.response.SaveBlogResponse;
import com.example.blog.entity.BlogPost;
import com.example.blog.exception.BadRequestException;
import com.example.blog.exception.DataNotFoundException;
import com.example.blog.repository.BlogRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class BlogService {

    private final BlogRepository blogRepository;

    @Transactional
    public BaseResponse<?> saveBlog(SaveBlogRequest request) {
        log.info("save blog req : {}", request);

        // validation input
        if (ObjectUtils.isEmpty(request.getTitle()))
            throw new BadRequestException("title can't be empty");
        if (ObjectUtils.isEmpty(request.getAuthor()))
            throw new BadRequestException("author can't be empty");
        if (ObjectUtils.isEmpty(request.getBody()))
            throw new BadRequestException("body can't be empty");
        try {
            // save blog
            var blogPost = blogRepository.save(BlogPost.builder()
                    .id(null)
                    .title(request.getTitle())
                    .author(request.getAuthor())
                    .body(request.getBody())
                    .createdBy(BlogConstant.SYSTEM)
                    .createdDate(BlogConstant.DATE_NOW)
                    .build());
            log.info("blog : {}", blogPost);
            if (ObjectUtils.isEmpty(blogPost))
                throw new DataNotFoundException("Failed save blog");

            // return response
            log.info("successfully save blog");
            return BaseResponse.ok(SaveBlogResponse.builder()
                    .isSuccess(true)
                    .build());
        } catch (BadRequestException e) {
            log.error("error e : {}", e.getMessage());
            throw e;
        }
    }

    @Transactional
    public BaseResponse<?> updateBlogById(UpdateBlogRequest request) {
        log.info("update blog by id req : {}", request);

        // validation input
        if (ObjectUtils.isEmpty(request.getId()) || request.getId() <= 0L)
            throw new BadRequestException("id not valid");
        if (ObjectUtils.isEmpty(request.getTitle()))
            throw new BadRequestException("title can't be empty");
        if (ObjectUtils.isEmpty(request.getAuthor()))
            throw new BadRequestException("author can't be empty");
        if (ObjectUtils.isEmpty(request.getBody()))
            throw new BadRequestException("body can't be empty");

        try {
            // update blog by id
            var blogPost = getBlogPostById(request.getId());
            log.info("blog : {}", blogPost);
            if (ObjectUtils.isEmpty(blogPost))
                throw new DataNotFoundException("id " + request.getId()+ " not found");

            blogRepository.save(BlogPost.builder()
                    .id(blogPost.getId())
                    .title(request.getTitle())
                    .author(request.getAuthor())
                    .body(request.getBody())
                    .createdBy(blogPost.getCreatedBy())
                    .createdDate(blogPost.getCreatedDate())
                    .modifiedBy(BlogConstant.SYSTEM)
                    .modifiedDate(BlogConstant.DATE_NOW)
                    .build());

            // return response
            log.info("successfully update blog by id");
            return BaseResponse.ok(SaveBlogResponse.builder()
                    .isSuccess(true)
                    .build());
        } catch (BadRequestException e) {
            log.error("error e : {}", e.getMessage());
            throw e;
        }
    }

    @Transactional
    public BaseResponse<?> deleteBlogById(DeleteBlogByIdRequest request) {
        log.info("delete blog by id req : {}", request);

        // validation input
        if (ObjectUtils.isEmpty(request.getId()) || request.getId() <= 0L)
            throw new BadRequestException("id not valid");

        try {
            // delete blog by id
            var blogPost = getBlogPostById(request.getId());
            log.info("blog : {}", blogPost);
            if (ObjectUtils.isEmpty(blogPost))
                throw new DataNotFoundException("id " + request.getId()+ " not found");

            blogRepository.deleteById(request.getId());

            // return response
            log.info("successfully delete blog");
            return BaseResponse.ok(SaveBlogResponse.builder()
                    .isSuccess(true)
                    .build());
        } catch (BadRequestException e) {
            log.error("error e : {}", e.getMessage());
            throw e;
        }
    }

    public BaseResponse<?> getBlogPostById(GetBlogByIdRequest request) {
        log.info("get blog by id req : {}", request);
        try {
            // validation input
            if (ObjectUtils.isEmpty(request.getId()) || request.getId() <= 0L)
                throw new BadRequestException("id not valid");

            // get blog by id
            var blogPost = getBlogPostById(request.getId());
            log.info("blog : {}", blogPost);
            if (ObjectUtils.isEmpty(blogPost))
                throw new DataNotFoundException(BlogConstant.DATA_NOT_FOUND);

            // return response
            return BaseResponse.ok(GetBlogResponse.builder()
                    .id(blogPost.getId())
                    .author(blogPost.getAuthor())
                    .title(blogPost.getTitle())
                    .body(blogPost.getBody())
                    .build());
        } catch (DataNotFoundException e) {
            log.error("error e : {}", e.getMessage());
            throw e;
        }
    }

    public BaseResponse<?> getAllBlog(GetAllBlogRequest request) {
        log.info("get all blog req : {}", request);

        // validation input
        if (ObjectUtils.isEmpty(request.getPageNo()) || request.getPageNo() <= 0)
            throw new BadRequestException("pageNo not valid");
        if (ObjectUtils.isEmpty(request.getPageSize()) || request.getPageSize() <= 0)
            throw new BadRequestException("pageSize not valid");

        try {
            // set paging
            var pageable = PageRequest.of(request.getPageNo()-1, request.getPageSize(), Sort.by("id").ascending());
            // get all blog by paging
            var page = blogRepository.findAll(pageable);
            log.info("blog list : {}", page.toList());
            if (CollectionUtils.isEmpty(page.toList())) {
                log.info(BlogConstant.DATA_NOT_FOUND);
                throw new DataNotFoundException(BlogConstant.DATA_NOT_FOUND);
            }

            // mapping list blog
            List<BlogPostDto> blogPostDtoList = new ArrayList<>();
            page.toList().forEach(blogPost ->
                    blogPostDtoList.add(BlogPostDto.builder()
                            .id(blogPost.getId())
                            .author(blogPost.getAuthor())
                            .title(blogPost.getTitle())
                            .body(blogPost.getBody())
                            .build())
            );

            // return response
            return BaseResponse.ok(GetAllBlogResponse.builder()
                    .blogPostDtoList(blogPostDtoList)
                    .totalPage(page.getTotalPages())
                    .totalElements(page.getTotalElements())
                    .build());
        } catch (DataNotFoundException e) {
            log.error("error e : {}", e.getMessage());
            throw e;
        }
    }

    /**
     * get blog by id then return blog or else null
     * @param blogId
     * @return blog
     */
    public BlogPost getBlogPostById(Long blogId) {
        return blogRepository.findById(blogId).orElse(null);
    }
}
