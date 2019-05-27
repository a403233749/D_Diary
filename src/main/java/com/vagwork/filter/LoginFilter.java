package com.vagwork.filter;

import com.vagwork.utils.StringHelper;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class LoginFilter implements Filter {
    /**
     * 不需要被过滤器拦截的页面 ，主要用于静态资源的放行
     * 在web.xml中配置filter的init-param
     */
    private String excludedPaths;
    private String [] excludedPathArray;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // 初始化时读取web.xml中配置的init-param
        excludedPaths = filterConfig.getInitParameter("excludedPaths");
        if(StringHelper.isNotBlank(excludedPaths)){
            excludedPathArray = excludedPaths.split(",");
        }
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        servletRequest.setCharacterEncoding("UTF-8");
        servletResponse.setCharacterEncoding("UTF-8");

        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpSession session = request.getSession();

        // 判断是否是直接放行的请求
        if(!isFilterExcludeRequest(request)) {
            Object o = session.getAttribute("currentUser");
            String path = request.getServletPath();
            if(o == null && path.indexOf("login") < 0) {
                request.getRequestDispatcher("index.jsp").forward(servletRequest,servletResponse);
            } else {
                filterChain.doFilter(servletRequest,servletResponse);
            }
        }else {
            filterChain.doFilter(servletRequest, servletResponse);
        }
    }

    @Override
    public void destroy() {

    }

    /**
     * 判断是否是 过滤器直接放行的请求
     * <br/>主要用于静态资源的放行
     * @param request
     * @return
     */
    private boolean isFilterExcludeRequest(HttpServletRequest request) {
        if(null != excludedPathArray && excludedPathArray.length > 0) {
            String url = request.getRequestURI();
            for (String ecludedUrl : excludedPathArray) {
                if (ecludedUrl.startsWith("*.")) {
                    // 如果配置的是后缀匹配, 则把前面的*号干掉，然后用endWith来判断
                    if(url.endsWith(ecludedUrl.substring(1))){
                        return true;
                    }
                }else if (ecludedUrl.endsWith("/*")) {
                    if(!ecludedUrl.startsWith("/")) {
                        // 前缀匹配，必须要是/开头
                        ecludedUrl = "/" + ecludedUrl;
                    }
                    // 如果配置是前缀匹配, 则把最后的*号干掉，然后startWith来判断
                    String prffixStr = request.getContextPath() + ecludedUrl.substring(0, ecludedUrl.length() - 1);
                    if(url.startsWith(prffixStr)) {
                        return true;
                    }
                } else {
                    // 如果不是前缀匹配也不是后缀匹配,那就是全路径匹配
                    if(!ecludedUrl.startsWith("/")) {
                        // 全路径匹配，也必须要是/开头
                        ecludedUrl = "/" + ecludedUrl;
                    }
                    String targetUrl = request.getContextPath() + ecludedUrl;
                    if(url.equals(targetUrl)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
