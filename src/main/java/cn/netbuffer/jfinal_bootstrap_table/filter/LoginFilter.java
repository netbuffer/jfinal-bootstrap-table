package cn.netbuffer.jfinal_bootstrap_table.filter;

import cn.netbuffer.jfinal_bootstrap_table.constant.ConfigConstant;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.builder.ToStringBuilder;
import javax.servlet.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
public class LoginFilter implements Filter {

    private FilterConfig filterConfig;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        this.filterConfig = filterConfig;
    }

    @Override
    public void doFilter(ServletRequest servletRequest,
                         ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        HttpServletResponse httpServletResponse = (HttpServletResponse) servletResponse;

        String noFilterTagString = filterConfig.getInitParameter("noFilterTags");
        boolean enable = Boolean.parseBoolean(filterConfig.getInitParameter("enable"));
        //不起用的情况下直接通过
        if (!enable) {
            filterChain.doFilter(httpServletRequest, httpServletResponse);
            return;
        }
        String[] noFilterTags = noFilterTagString.split(";");

        String uri = httpServletRequest.getRequestURI();
//		log.debug("过滤路径:{}",uri);
        // 配置文件中允许放行的关键字
        if (noFilterTags != null && noFilterTags.length > 0) {
            for (String noFilterTag : noFilterTags) {
                if (noFilterTag == null || "".equals(noFilterTag.trim())) {
                    continue;
                }
                if (uri.indexOf(noFilterTag.trim()) != -1) {
                    filterChain.doFilter(httpServletRequest, httpServletResponse);
                    return;
                }
            }
        }

        Cookie[] cookies = httpServletRequest.getCookies();
        log.debug("path:{}", uri);
        Object islogin = httpServletRequest.getSession().getAttribute(ConfigConstant.ISLOGIN);
        if (islogin != null && Boolean.parseBoolean(islogin.toString())) {
            log.debug("p1");
            filterChain.doFilter(httpServletRequest, httpServletResponse);
        } else if (cookies != null) {
            log.debug("p2");
            for (Cookie cookie : cookies) {
//				log.debug("cookiename:"+cookie.getName());
                if (cookie.getName().equals(ConfigConstant.USERNAME)) {
                    log.debug("cookie:" + ToStringBuilder.reflectionToString(cookie));
                    httpServletRequest.getSession().setAttribute(ConfigConstant.ISLOGIN, true);
                    httpServletRequest.getSession().setAttribute(ConfigConstant.USERNAME, cookie.getValue());
                    if (uri.endsWith(ConfigConstant.PROJECTNAME + "/")) {
                        httpServletResponse.sendRedirect(httpServletRequest.getContextPath() + "/manage.html");
                    } else {
                        filterChain.doFilter(httpServletRequest, httpServletResponse);
                    }
                    return;
                }
            }
            httpServletResponse.sendRedirect(httpServletRequest
                    .getContextPath() + "/index.html");
        }
    }

    @Override
    public void destroy() {
    }
}
