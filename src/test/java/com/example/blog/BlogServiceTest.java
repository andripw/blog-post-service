package com.example.blog;

import com.example.blog.constant.BlogConstant;
import com.example.blog.dto.request.*;
import com.example.blog.entity.BlogPost;
import com.example.blog.exception.BadRequestException;
import com.example.blog.exception.DataNotFoundException;
import com.example.blog.repository.BlogRepository;
import com.example.blog.service.BlogService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@RunWith(value = SpringRunner.class)
@RestClientTest(BlogService.class)
@ContextConfiguration(classes = BlogService.class)
@Slf4j
public class BlogServiceTest {

    @MockBean
    private BlogRepository blogRepository;

    @Autowired
    private BlogService blogService;

    private final BlogPost blogPost = BlogPost.builder()
            .id(1L)
            .title("test")
            .body("test")
            .author("test")
            .createdDate(BlogConstant.DATE_NOW)
            .createdBy(BlogConstant.SYSTEM)
            .modifiedBy(BlogConstant.SYSTEM)
            .modifiedDate(BlogConstant.DATE_NOW)
            .build();

    private final List<BlogPost> blogPostList = new ArrayList<>();

    @Test
    public void saveBlog_success() {
        when(blogRepository.save(any())).thenReturn(blogPost);
        var response = blogService.saveBlog(SaveBlogRequest.builder()
                .title("test")
                .body("test")
                .author("test")
                .build());
        assertNotNull(response);
    }

    @Test(expected = BadRequestException.class)
    public void saveBlog_expectTitleIsEmpty() {
        blogService.saveBlog(SaveBlogRequest.builder()
                .title("")
                .body("test")
                .author("test")
                .build());
    }

    @Test(expected = BadRequestException.class)
    public void saveBlog_expectBodyIsEmpty() {
        blogService.saveBlog(SaveBlogRequest.builder()
                .title("test")
                .body("")
                .author("test")
                .build());
    }

    @Test(expected = BadRequestException.class)
    public void saveBlog_expectAuthorIsEmpty() {
        blogService.saveBlog(SaveBlogRequest.builder()
                .title("test")
                .body("test")
                .author("")
                .build());
    }

    @Test(expected = DataNotFoundException.class)
    public void saveBlog_expectFailed() {
        when(blogRepository.save(any())).thenReturn(null);
        blogService.saveBlog(SaveBlogRequest.builder()
                .title("test")
                .body("test")
                .author("test")
                .build());
    }

    @Test
    public void updateBlogById_success() {
        when(blogRepository.findById(anyLong())).thenReturn(Optional.of(blogPost));
        blogService.updateBlogById(UpdateBlogRequest.builder()
                .id(1L)
                .title("test")
                .body("test")
                .author("test")
                .build());
    }

    @Test(expected = BadRequestException.class)
    public void updateBlogById_expectIdINotValid() {
        when(blogRepository.findById(anyLong())).thenReturn(Optional.of(blogPost));
        blogService.updateBlogById(UpdateBlogRequest.builder()
                .id(null)
                .title("test")
                .body("test")
                .author("test")
                .build());
    }

    @Test(expected = BadRequestException.class)
    public void updateBlogById_expectTitleIsEmpty() {
        when(blogRepository.findById(anyLong())).thenReturn(Optional.of(blogPost));
        blogService.updateBlogById(UpdateBlogRequest.builder()
                .id(1L)
                .title("")
                .body("test")
                .author("test")
                .build());
    }

    @Test(expected = BadRequestException.class)
    public void updateBlogById_expectAuthorIsEmpty() {
        when(blogRepository.findById(anyLong())).thenReturn(Optional.of(blogPost));
        blogService.updateBlogById(UpdateBlogRequest.builder()
                .id(1L)
                .title("test")
                .body("test")
                .author("")
                .build());
    }

    @Test(expected = BadRequestException.class)
    public void updateBlogById_expectBodyIsEmpty() {
        when(blogRepository.findById(anyLong())).thenReturn(Optional.of(blogPost));
        blogService.updateBlogById(UpdateBlogRequest.builder()
                .id(1L)
                .title("test")
                .body("")
                .author("test")
                .build());
    }

    @Test(expected = DataNotFoundException.class)
    public void updateBlogById_expectDataNotFound() {
        when(blogRepository.findById(anyLong())).thenReturn(Optional.empty());
        blogService.updateBlogById(UpdateBlogRequest.builder()
                .id(1L)
                .title("test")
                .body("test")
                .author("test")
                .build());
    }

    @Test
    public void deleteBlogById_success() {
        when(blogRepository.findById(anyLong())).thenReturn(Optional.of(blogPost));
        blogService.deleteBlogById(DeleteBlogByIdRequest.builder()
                .id(1L)
                .build());
    }

    @Test(expected = BadRequestException.class)
    public void deleteBlogById_expectIdNotValid() {
        when(blogRepository.findById(anyLong())).thenReturn(Optional.of(blogPost));
        blogService.deleteBlogById(DeleteBlogByIdRequest.builder()
                .id(null)
                .build());
    }

    @Test(expected = DataNotFoundException.class)
    public void deleteBlogById_expectDataNotFound() {
        when(blogRepository.findById(anyLong())).thenReturn(Optional.empty());
        blogService.deleteBlogById(DeleteBlogByIdRequest.builder()
                .id(1L)
                .build());
    }

    @Test
    public void getBlogPostById_success() {
        GetBlogByIdRequest request = GetBlogByIdRequest.builder()
                .id(1L)
                .build();
        when(blogRepository.findById(anyLong())).thenReturn(Optional.of(blogPost));

        var response = blogService.getBlogPostById(request);
        assertNotNull(response);
    }

    @Test(expected = BadRequestException.class)
    public void getBlogPostById_expectIdNotValid() {
        GetBlogByIdRequest request = GetBlogByIdRequest.builder()
                .id(null)
                .build();
        when(blogRepository.findById(anyLong())).thenReturn(Optional.of(blogPost));

        blogService.getBlogPostById(request);
    }

    @Test(expected = DataNotFoundException.class)
    public void getBlogPostById_expectDataNotFound() {
        GetBlogByIdRequest request = GetBlogByIdRequest.builder()
                .id(1L)
                .build();
        when(blogRepository.findById(anyLong())).thenReturn(Optional.empty());

        blogService.getBlogPostById(request);
    }

    @Test
    public void getAllBlog_success() {
        GetAllBlogRequest request = GetAllBlogRequest.builder()
                .pageNo(1)
                .pageSize(20)
                .build();

        blogPostList.add(blogPost);
        Page<BlogPost> blogPostPage = new PageImpl<>(blogPostList);

        when(blogRepository.findAll(any(Pageable.class))).thenReturn(blogPostPage);
        var response = blogService.getAllBlog(request);
        assertNotNull(response);
    }

    @Test(expected = BadRequestException.class)
    public void getAllBlog_expectPageNoNotValid() {
        GetAllBlogRequest request = GetAllBlogRequest.builder()
                .pageNo(0)
                .pageSize(20)
                .build();
        blogService.getAllBlog(request);
    }

    @Test(expected = BadRequestException.class)
    public void getAllBlog_expectPageSizeNotValid() {
        GetAllBlogRequest request = GetAllBlogRequest.builder()
                .pageNo(1)
                .pageSize(0)
                .build();
        blogService.getAllBlog(request);
    }

    @Test(expected = DataNotFoundException.class)
    public void getAllBlog_expectDataNotFound() {
        GetAllBlogRequest request = GetAllBlogRequest.builder()
                .pageNo(1)
                .pageSize(20)
                .build();

        when(blogRepository.findAll(any(Pageable.class))).thenReturn(Page.empty());
        blogService.getAllBlog(request);
    }
}
