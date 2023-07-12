package com.example.blog;

import com.example.blog.controller.BlogController;
import com.example.blog.dto.request.*;
import com.example.blog.service.BlogService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = BlogController.class)
@WebMvcTest(controllers = BlogController.class)
@Slf4j
public class BlogControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BlogService blogService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void saveBlog_success() throws Exception {
        mockMvc.perform(post("/blog/save")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(new SaveBlogRequest()))
        ).andExpect(status().isOk());
    }

    @Test
    public void getBlogById_success() throws Exception {
        mockMvc.perform(post("/blog/get")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(new GetBlogByIdRequest()))
        ).andExpect(status().isOk());
    }

    @Test
    public void getAllBlog_success() throws Exception {
        mockMvc.perform(post("/blog/get/all")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(new GetAllBlogRequest()))
        ).andExpect(status().isOk());
    }

    @Test
    public void updateBlogById_success() throws Exception {
        mockMvc.perform(post("/blog/update")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(new UpdateBlogRequest()))
        ).andExpect(status().isOk());
    }

    @Test
    public void deleteBlog_success() throws Exception {
        mockMvc.perform(post("/blog/delete")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(new DeleteBlogByIdRequest()))
        ).andExpect(status().isOk());
    }
}
