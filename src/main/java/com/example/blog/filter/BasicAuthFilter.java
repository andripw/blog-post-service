package com.example.blog.filter;

import com.example.blog.dto.BaseResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.micrometer.core.instrument.util.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Configuration
public class BasicAuthFilter extends OncePerRequestFilter {

    private final ObjectMapper mapper;
    private final String key;

    public BasicAuthFilter(
            @Value("${auth.public}")String key,
            ObjectMapper mapper) {
        this.key = key;
        this.mapper = mapper;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if (RequestMethod.OPTIONS.name().equals(request.getMethod())) {
            response.setStatus(HttpServletResponse.SC_OK);
            filterChain.doFilter(request, response);
        }

        if (StringUtils.isBlank(request.getHeader(HttpHeaders.AUTHORIZATION)) || request.getHeader(HttpHeaders.AUTHORIZATION) == null) {
            generateResponse(response, "Authorization Header is Required");
            response.getWriter().flush();
            return;
        }

        String auth = request.getHeader(HttpHeaders.AUTHORIZATION).replace("Basic ", "");

        if (!auth.equals(key)) {
            generateResponse(response, "Invalid Token");
            response.getWriter().flush();
            return;
        }
        filterChain.doFilter(request, response);
    }

    private void generateResponse(HttpServletResponse response, String message) {
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        try {
            response.getWriter().print(mapper.writeValueAsString(BaseResponse.unauthorized(message)));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
