package com.chuan.on_my_way.filter;

import com.alibaba.fastjson.JSON;
import com.chuan.on_my_way.utility.BaseContext;
import com.chuan.on_my_way.utility.R;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.util.AntPathMatcher;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 检查登用户是否完成登录
 */
@WebFilter(filterName = "loginCheckFilter", urlPatterns = "/*")
@Slf4j
public class LoginCheckFilter implements Filter {
    //路径匹配器
    public static final AntPathMatcher PATH_MATCHER = new AntPathMatcher();

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request  = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        String requestURI = request.getRequestURI();

        //不需要处理
        String[] uris = new String[]{
                "/employee/login",
                "/employee/logout",
                "/backend/**",
                "/front/**",
                "/user/sendMsg",
                "/user/login"
        };
        boolean check = check(uris,requestURI);
        log.info("拦截到的请求: {}", requestURI);
        if (check){
            filterChain.doFilter(request,response);
            return;
        }

        if (request.getSession().getAttribute("employee")!=null){
            Long current = (Long) request.getSession().getAttribute("employee");
            BaseContext.setCurrent(current);
            filterChain.doFilter(request,response);
            return;
        }

        if (request.getSession().getAttribute("user")!=null){
            Long current = (Long) request.getSession().getAttribute("user");
            BaseContext.setCurrent(current);
            filterChain.doFilter(request,response);
            return;
        }

        response.getWriter().write(JSON.toJSONString(R.error("NOTLOGIN")));
        return;

    }

    public boolean check(String[] uris, String requestURI){
        for (String url : uris){
            boolean match = PATH_MATCHER.match(url, requestURI);
            if (match){
                return true;
            }
        }
        return false;

    }
}
