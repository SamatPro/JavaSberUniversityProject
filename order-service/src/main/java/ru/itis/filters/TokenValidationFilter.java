package ru.itis.filters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.itis.utils.TokenUtil;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class TokenValidationFilter implements Filter {

    @Autowired
    private TokenUtil tokenUtil;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        String authHeader = request.getHeader("Authorization");
        System.out.println(authHeader);
        if (!request.getMethod().equals("OPTIONS")) {
            if (authHeader != null && authHeader.contains("Bearer")){
                String token = request.getHeader("Authorization").substring("Bearer".length()).trim();
                if (!tokenUtil.validateToken(token)) {
//                    response.sendError(403);
//                    return;
                }
            } else  {
                response.sendError(403);
                return;
            }
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {

    }
}
