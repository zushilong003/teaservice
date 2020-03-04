package com.lingtian.pmrApi.Filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@WebFilter("/*")
public class AuthSessionFilter implements Filter {
    private final boolean ENABLE_TEST = true;


    private ObjectMapper objectMapper;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        objectMapper = new ObjectMapper();
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;
        log.info(request.getRequestURI());
        request.setCharacterEncoding("UTF-8");
        response.setContentType("application/json;charset=UTF-8");
        chain.doFilter(request, response);//将请求转到目的地址
//        if (request.getRequestURI().contains("/pmEmr/cy")) {
//            chain.doFilter(request, response);//将请求转到目的地址
//        } else {
//            String token = request.getHeader("token");
//            if (token == null || token.trim().equals("")) {
//                token = request.getHeader("Token");
//            }
//
//            if (token == null || token.trim().equals(""))//去掉字符串首尾的空格
//            {
//                // 不传 token，报错
//                response.setStatus(403);
//                response.setContentType("application/json;charset=UTF-8");
//                DataCluserResult res = new DataCluserResult("403", "请传token");
//                PrintWriter writer = response.getWriter();
//                objectMapper.writeValue(writer, res);
//                writer.close();
//                return;
//            }
//            chain.doFilter(request, response);//将请求转到目的地址
//        }
    }

    @Override
    public void destroy() {

    }
}
